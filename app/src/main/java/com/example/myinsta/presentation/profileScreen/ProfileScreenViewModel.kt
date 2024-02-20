package com.example.myinsta.presentation.profileScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myinsta.common.Constants
import com.example.myinsta.common.Constants.TAG
import com.example.myinsta.models.Post
import com.example.myinsta.models.User
import com.example.myinsta.response.Resource
import com.example.myinsta.useCases.FirebaseGetPostsInfoUseCase
import com.example.myinsta.useCases.FirebaseGetPostsListIds
import com.example.myinsta.useCases.FirebaseGetUserIdUseCase
import com.example.myinsta.useCases.FirebaseGetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel
@Inject constructor(
    firebaseGetUserIdUseCase: FirebaseGetUserIdUseCase,
    private val firebaseGetUserInfoUseCase: FirebaseGetUserInfoUseCase,
    private val firebaseGetPostsInfoUseCase: FirebaseGetPostsInfoUseCase,
    private val firebaseGetPostsListIds: FirebaseGetPostsListIds
) : ViewModel() {
    private val _userId = MutableStateFlow<String?>(null)
    val userId = _userId.asStateFlow()

    private val _userInfo = MutableStateFlow<User?>(null)
    val userInfo = _userInfo.asStateFlow()

    private val _postsList = MutableStateFlow<List<Post>>(emptyList())
    val postsList = _postsList.asStateFlow()
    private val _postsIdsList = MutableStateFlow<List<String>>(emptyList())
    val postsIdsList = _postsIdsList.asStateFlow()
    val loading = mutableStateOf(false)
    data class UserDataWrapper(
        val t1: Resource<User?>,
        val t2: Resource<List<String>>,
        val t3: MutableList<Post> = mutableListOf()
    )
    init {
        firebaseGetUserIdUseCase.execute()?.let { user ->
            _userId.value = user.uid
            getFlows(userId.value.toString())
        }
    }
    private fun getFlows(id: String) = viewModelScope.launch {
        firebaseGetUserInfoUseCase.execute(
            userId = id
        ).zip(
            firebaseGetPostsListIds
                .execute(id)
        ) { t1, t2 ->
            UserDataWrapper(t1, t2)
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
                loading.value = false
            }catch (e:Exception){
                e.localizedMessage ?: Constants.ERROR
                loading.value = false
            }
        }
    }
    fun getUserInfo(id: String) = viewModelScope.launch {
        firebaseGetUserInfoUseCase.execute(
            userId = id
        ).catch {
            Log.d(TAG, "Error ${it.message}")
        }.collect { response ->
            when (response) {
                is Resource.Error -> {
                    Log.d(TAG, "Error response")
                }

                is Resource.Loading -> {
                    Log.d(TAG, "Loading")
                }

                is Resource.Success -> {
                    _userInfo.value = response.data ?: User()
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
}