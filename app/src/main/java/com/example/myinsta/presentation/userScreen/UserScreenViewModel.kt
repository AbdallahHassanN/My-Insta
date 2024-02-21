package com.example.myinsta.presentation.userScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myinsta.common.Constants
import com.example.myinsta.common.Constants.TAG
import com.example.myinsta.models.Post
import com.example.myinsta.models.User
import com.example.myinsta.presentation.profileScreen.ProfileScreenViewModel
import com.example.myinsta.response.Resource
import com.example.myinsta.useCases.FirebaseFollowUser
import com.example.myinsta.useCases.FirebaseGetFollowersInfoUseCase
import com.example.myinsta.useCases.FirebaseGetFollowersListIds
import com.example.myinsta.useCases.FirebaseGetFollowingInfoUseCase
import com.example.myinsta.useCases.FirebaseGetFollowingListIds
import com.example.myinsta.useCases.FirebaseGetPostsInfoUseCase
import com.example.myinsta.useCases.FirebaseGetPostsListIds
import com.example.myinsta.useCases.FirebaseGetUserIdUseCase
import com.example.myinsta.useCases.FirebaseGetUserInfoUseCase
import com.example.myinsta.useCases.FirebaseUnfollowUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserScreenViewModel
@Inject constructor(
    private val firebaseGetUserInfoUseCase: FirebaseGetUserInfoUseCase,
    private val firebaseFollowUser: FirebaseFollowUser,
    private val firebaseUnfollowUser: FirebaseUnfollowUser,
    private val firebaseGetUserIdUseCase: FirebaseGetUserIdUseCase,
    private val firebaseGetFollowersListIds: FirebaseGetFollowersListIds,
    private val firebaseGetFollowersInfoUseCase: FirebaseGetFollowersInfoUseCase,
    private val firebaseGetFollowingInfoUseCase: FirebaseGetFollowingInfoUseCase,
    private val firebaseGetFollowingListIds: FirebaseGetFollowingListIds,
    private val firebaseGetPostsInfoUseCase: FirebaseGetPostsInfoUseCase,
    private val firebaseGetPostsListIds: FirebaseGetPostsListIds
) : ViewModel() {
    private val _userId = MutableStateFlow<String?>(null)
    val userId = _userId.asStateFlow()

    private val _currentUserId = MutableStateFlow<String?>(null)
    val currentUserId = _currentUserId.asStateFlow()

    private val _userInfo = MutableStateFlow<User?>(null)
    val userInfo = _userInfo.asStateFlow()

    private val _followState = MutableStateFlow<Boolean?>(false)

    private val _followersIdsList = MutableStateFlow<List<String>>(emptyList())
    val followersIdsList = _followersIdsList.asStateFlow()

    private val _followersList = MutableStateFlow<List<User>>(emptyList())
    val followersList = _followersList.asStateFlow()

    private val _followingIdsList = MutableStateFlow<List<String>>(emptyList())
    val followingIdsList = _followingIdsList.asStateFlow()

    private val _followingList = MutableStateFlow<List<User>>(emptyList())
    val followingList = _followingList.asStateFlow()


    private val _postsList = MutableStateFlow<List<Post>>(emptyList())
    val postsList = _postsList.asStateFlow()
    private val _postsIdsList = MutableStateFlow<List<String>>(emptyList())

    val loading = mutableStateOf(false)

    init {
        firebaseGetUserIdUseCase.execute()?.let { user ->
            _currentUserId.value = user.uid
            getFlows(_userId.value.toString())
            Log.d(TAG, "user2 in viewmodel ${_userId.value}")
        }
    }

    fun getFlows(id: String) = viewModelScope.launch {
        firebaseGetUserInfoUseCase.execute(
            userId = id
        ).zip(
            firebaseGetPostsListIds
                .execute(id)
        ) { t1, t2 ->
            ProfileScreenViewModel.UserDataWrapper(t1, t2)
        }.map {
            it.t2.data?.onEach { id ->
                it.t3.add(
                    firebaseGetPostsInfoUseCase
                        .execute(id)
                        .first()
                        .data!!
                )
            }
            it
        }.collect { response ->
            loading.value = true
            try {
                _userInfo.value = response.t1.data ?: User()
                _postsList.value = response.t3
                Log.d(TAG, "posts in viewmodel ${_postsList.value}")
                Log.d(TAG, "user in viewmodel ${_userInfo.value}")
                loading.value = false
            } catch (e: Exception) {
                e.localizedMessage ?: Constants.ERROR
                loading.value = false
            }
        }
    }

    fun getFollowersList(ids: List<String>) = viewModelScope.launch {
        val allFollowers = mutableListOf<User>()
        val jobs = mutableListOf<Deferred<List<User>>>()
        for (id in ids) {
            val job = async {
                firebaseGetFollowersInfoUseCase.execute(id).first().data ?: emptyList()
            }
            jobs.add(job)
        }
        jobs.awaitAll().forEach { followers ->
            allFollowers.addAll(followers)
        }
        _followersList.value = allFollowers
    }

    fun getFollowersListIds(id: String) {
        viewModelScope.launch {
            firebaseGetFollowersListIds
                .execute(id)
                .collect { response ->
                    when (response) {
                        is Resource.Error -> {
                            loading.value = false
                            Log.d(TAG, Constants.ERROR)
                        }

                        is Resource.Loading -> {
                            loading.value = true
                            Log.d(TAG, "Loading")
                        }

                        is Resource.Success -> {
                            _followersIdsList.value = response.data!!
                            loading.value = false
                        }
                    }
                }
        }
    }

    fun getFollowingList(ids: List<String>) = viewModelScope.launch {
        val allFollowers = mutableListOf<User>()
        val jobs = mutableListOf<Deferred<List<User>>>()
        for (id in ids) {
            val job = async {
                firebaseGetFollowingInfoUseCase.execute(id).first().data ?: emptyList()
            }
            jobs.add(job)
        }
        jobs.awaitAll().forEach { followers ->
            allFollowers.addAll(followers)
        }
        _followingList.value = allFollowers
    }

    fun getFollowingListIds(id: String) {
        viewModelScope.launch {
            firebaseGetFollowingListIds
                .execute(id)
                .collect { response ->
                    when (response) {
                        is Resource.Error -> {
                            loading.value = false
                            Log.d(TAG, Constants.ERROR)
                        }

                        is Resource.Loading -> {
                            loading.value = true
                            Log.d(TAG, "Loading")
                        }

                        is Resource.Success -> {
                            _followingIdsList.value = response.data!!
                            loading.value = false
                        }
                    }
                }
        }
    }

    fun followUser(id: String) {
        viewModelScope.launch {
            firebaseFollowUser
                .execute(id)
                .collect { response ->
                    when (response) {
                        is Resource.Error -> {
                            Log.d(TAG, Constants.ERROR)
                        }

                        is Resource.Loading -> {
                            Log.d(TAG, "Loading")
                        }

                        is Resource.Success -> {
                            Log.d(TAG, "Success")
                        }
                    }
                }
        }
    }

    fun unfollowUser(id: String) {
        viewModelScope.launch {
            firebaseUnfollowUser
                .execute(id)
                .collect { response ->
                    when (response) {
                        is Resource.Error -> {
                            Log.d(TAG, Constants.ERROR)
                        }

                        is Resource.Loading -> {
                            Log.d(TAG, "Loading")
                        }

                        is Resource.Success -> {
                            Log.d(TAG, "Success")
                        }
                    }
                }
        }
    }

    fun getAllPostsListIds(id: String) {
        viewModelScope.launch {
            firebaseGetPostsListIds
                .execute(id)
                .collect { response ->
                    when (response) {
                        is Resource.Error -> {
                            loading.value = false
                            Log.d(TAG, Constants.ERROR)
                        }

                        is Resource.Loading -> {
                            loading.value = true
                            Log.d(TAG, "Loading")
                        }

                        is Resource.Success -> {
                            _postsIdsList.value = response.data!!
                            loading.value = false
                        }
                    }
                }
        }
    }

    fun changeToFollowState() {
        _followState.value = true
    }

    fun changeToUnfollowState() {
        _followState.value = false
    }
}