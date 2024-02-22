package com.example.myinsta.presentation.feedScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myinsta.common.Constants
import com.example.myinsta.common.Constants.TAG
import com.example.myinsta.models.Comment
import com.example.myinsta.models.Post
import com.example.myinsta.models.User
import com.example.myinsta.response.Resource
import com.example.myinsta.useCases.FirebaseAddCommentUseCase
import com.example.myinsta.useCases.FirebaseAddLikeUseCase
import com.example.myinsta.useCases.FirebaseGetCommentsUseCase
import com.example.myinsta.useCases.FirebaseGetFollowingInfoUseCase
import com.example.myinsta.useCases.FirebaseGetFollowingListIds
import com.example.myinsta.useCases.FirebaseGetPostUseCase
import com.example.myinsta.useCases.FirebaseGetUserIdUseCase
import com.example.myinsta.useCases.FirebaseGetUserInfoUseCase
import com.example.myinsta.useCases.FirebaseGetUsersPostsUseCase
import com.example.myinsta.useCases.FirebaseRemoveLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedScreenViewModel
@Inject constructor(
    private val firebaseGetUserIdUseCase: FirebaseGetUserIdUseCase,
    private val firebaseGetFollowingInfoUseCase: FirebaseGetFollowingInfoUseCase,
    private val firebaseGetFollowingListIds: FirebaseGetFollowingListIds,
    private val firebaseGetUserInfoUseCase: FirebaseGetUserInfoUseCase,
    private val firebaseGetUsersPostsUseCase: FirebaseGetUsersPostsUseCase,
    private val firebaseAddCommentUseCase: FirebaseAddCommentUseCase,
    private val firebaseGetPostUseCase: FirebaseGetPostUseCase,
    private val firebaseGetCommentsUseCase: FirebaseGetCommentsUseCase,
    private val firebaseAddLikeUseCase: FirebaseAddLikeUseCase,
    private val firebaseRemoveLikeUseCase: FirebaseRemoveLikeUseCase,
) : ViewModel() {

    private val _currentUserId = MutableStateFlow<String>("")
    val currentUserId = _currentUserId.asStateFlow()

    private val _userInfo = MutableStateFlow<User?>(null)
    val userInfo = _userInfo.asStateFlow()

    private val _followingIdsList = MutableStateFlow<List<String>>(emptyList())

    private val _followingList = MutableStateFlow<List<User>>(emptyList())
    val followingList = _followingList.asStateFlow()

    private val _userName = MutableStateFlow<String>("")
    val userName = _userName.asStateFlow()

    private val _postsList = MutableStateFlow<List<Post?>>(emptyList())
    val postsList = _postsList.asStateFlow()

    private val _commentsList = MutableStateFlow<List<Comment?>>(emptyList())
    val commentsList = _commentsList.asStateFlow()

    private val _likesList = MutableStateFlow<List<Comment?>>(emptyList())
    val likesList = _likesList.asStateFlow()

    private val _postData = MutableStateFlow<Post?>(null)
    val postData = _postData.asStateFlow()

    val loading = mutableStateOf(false)


    init {
        firebaseGetUserIdUseCase.execute()?.let {
            _currentUserId.value = it.uid
            _userName.value = it.displayName.toString()
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
            getUsersPosts(_followingIdsList.value)
        }
    }

    fun getComments(postId: String) {
        viewModelScope.launch {
            firebaseGetCommentsUseCase.execute(postId)
                .collect { response ->
                    when (response) {
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
                            _commentsList.value = response.data!!.sortedByDescending {
                                it.time
                            }
                        }
                    }
                }
        }
    }

    private fun getUsersPosts(idsList: List<String>) {
        viewModelScope.launch {
            firebaseGetUsersPostsUseCase.execute(idsList).collect { response ->
                when (response) {
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
                        _postsList.value = response.data!!.sortedByDescending {
                            it.time
                        }
                    }
                }

            }
        }
    }

    private fun getFollowingList(ids: List<String>) = viewModelScope.launch {
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

    private fun getFollowingListIds(id: String) {
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

    fun getPost(postId: String) = viewModelScope.launch {
        firebaseGetPostUseCase.execute(postId).collect {
            _postData.value = it.data
        }
    }

    fun addComment(
        postId: String,
        comment: String,
        userId: String,
        authorName: String,
    ) {
        viewModelScope.launch {
            firebaseAddCommentUseCase
                .execute(
                    postId,
                    comment,
                    userId,
                    authorName
                )
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
                            Log.d(TAG, "Success")
                            loading.value = false
                        }
                    }
                }
        }
    }

    fun addLike(
        postId: String,
        userId: String,
        userName: String
    ) {
        viewModelScope.launch {
            firebaseAddLikeUseCase
                .execute(
                    postId = postId,
                    userId = userId,
                    userName = userName
                )
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
                            Log.d("Add Like", (response.data ?: response.message).toString())
                            Log.d(TAG, "Like Success")
                            loading.value = false
                        }
                    }
                }
        }
    }

    fun removeLike(
        userId: String,
        postId: String,
        likeId: String
    ) {
        viewModelScope.launch {
            firebaseRemoveLikeUseCase
                .execute(
                    postId = postId,
                    userId = userId,
                    likeId = likeId
                )
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
                            Log.d("Remove Like", (response.data ?: response.message).toString())
                            Log.d(TAG, "Remove Success")
                            loading.value = false
                        }
                    }
                }
        }
    }
}