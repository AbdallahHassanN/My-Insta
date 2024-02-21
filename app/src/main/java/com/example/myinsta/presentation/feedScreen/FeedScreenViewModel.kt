package com.example.myinsta.presentation.feedScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myinsta.common.Constants
import com.example.myinsta.common.Constants.TAG
import com.example.myinsta.common.validator.EmptyValidator
import com.example.myinsta.common.validator.ValidateResult
import com.example.myinsta.models.Post
import com.example.myinsta.models.User
import com.example.myinsta.presentation.profileScreen.ProfileScreenViewModel
import com.example.myinsta.response.Resource
import com.example.myinsta.useCases.FirebaseGetFollowingInfoUseCase
import com.example.myinsta.useCases.FirebaseGetFollowingListIds
import com.example.myinsta.useCases.FirebaseGetPostsInfoUseCase
import com.example.myinsta.useCases.FirebaseGetPostsListIds
import com.example.myinsta.useCases.FirebaseGetUserIdUseCase
import com.example.myinsta.useCases.FirebaseGetUserInfoUseCase
import com.example.myinsta.useCases.FirebaseGetUsersPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedScreenViewModel
@Inject constructor(
    private val firebaseGetUserIdUseCase: FirebaseGetUserIdUseCase,
    private val firebaseGetFollowingInfoUseCase: FirebaseGetFollowingInfoUseCase,
    private val firebaseGetFollowingListIds: FirebaseGetFollowingListIds,
    private val firebaseGetUserInfoUseCase: FirebaseGetUserInfoUseCase,
    private val firebaseGetUsersPostsUseCase: FirebaseGetUsersPostsUseCase
) : ViewModel() {

    private val _currentUserId = MutableStateFlow<String>("")
    val currentUserId = _currentUserId.asStateFlow()

    private val _userInfo = MutableStateFlow<User?>(null)
    val userInfo = _userInfo.asStateFlow()

    private val _followingIdsList = MutableStateFlow<List<String>>(emptyList())
    val followingIdsList = _followingIdsList.asStateFlow()

    private val _followingList = MutableStateFlow<List<User>>(emptyList())
    val followingList = _followingList.asStateFlow()

    private val _postsList = MutableStateFlow<List<Post?>>(emptyList())
    val postsList = _postsList.asStateFlow()
    private val _postsIdsList = MutableStateFlow<List<String>>(emptyList())

    private val _commentValue = MutableStateFlow("")
    val commentValue = _commentValue.asStateFlow()

    val commentValidation = mutableStateOf<ValidateResult?>(null)

    val loading = mutableStateOf(false)

    init {
        firebaseGetUserIdUseCase.execute()?.let {
            _currentUserId.value = it.uid
        }
        viewModelScope.launch {
            _followingIdsList.collect {
                getUsersDataOfFollowing()
            }
        }
    }

    fun getUsersDataOfFollowing() = viewModelScope.launch {
        getFollowingListIds(_currentUserId.value)
        getFollowingList(_followingIdsList.value).apply {
            getUsersPosts(_followingIdsList.value).apply {

            }
        }
    }
    fun getUsersPosts(idsList: List<String>) {
        viewModelScope.launch {
            firebaseGetUsersPostsUseCase.execute(idsList).collect { it ->
                when (it) {
                    is Resource.Error -> {
                        loading.value = false
                        Log.d(TAG, "ERROR")
                    }
                    is Resource.Loading -> {
                        loading.value = true
                        Log.d(TAG, "Loading")
                    }
                    is Resource.Success -> {
                        loading.value = false
                        _postsList.value = it.data!!.sortedByDescending {
                            it.time
                        }
                    }
                }

            }
        }
    }

    fun getFollowingList(ids: List<String>) = viewModelScope.launch {
        val allFollowing = mutableListOf<User>()
        val jobs = mutableListOf<Deferred<List<User>>>()
        for (id in ids) {
            val job = async {
                firebaseGetFollowingInfoUseCase.execute(id).first().data ?: emptyList()
            }
            jobs.add(job)
        }
        jobs.awaitAll().forEach { followers ->
            allFollowing.addAll(followers)
        }
        _followingList.value = allFollowing
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
    fun onCommentChanged(newQuery: String) {
        _commentValue.value = newQuery
    }
    private fun validateComment() = EmptyValidator(_commentValue.value).validate()

}