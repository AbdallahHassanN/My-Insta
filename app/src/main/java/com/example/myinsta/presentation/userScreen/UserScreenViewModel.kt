package com.example.myinsta.presentation.userScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myinsta.common.Constants
import com.example.myinsta.common.Constants.TAG
import com.example.myinsta.models.User
import com.example.myinsta.response.Resource
import com.example.myinsta.useCases.FirebaseFollowUser
import com.example.myinsta.useCases.FirebaseGetUserIdUseCase
import com.example.myinsta.useCases.FirebaseGetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserScreenViewModel
@Inject constructor(
    private val firebaseGetUserInfoUseCase: FirebaseGetUserInfoUseCase,
    private val firebaseFollowUser: FirebaseFollowUser
) : ViewModel() {
    private val _userId = MutableStateFlow<String?>(null)
    val userId = _userId.asStateFlow()

    private val _userInfo = MutableStateFlow<User?>(null)
    val userInfo = _userInfo.asStateFlow()

    init {
        getUserInfo(_userId.value.toString())
    }

    fun getUserInfo(id:String) = viewModelScope.launch {
        firebaseGetUserInfoUseCase.execute(
            userId = id
        ).catch {
            Log.d(TAG,"Error ${it.message}")
        }.collect{ response ->
            when(response) {
                is Resource.Error -> {
                    Log.d(TAG, "Error response")
                }
                is Resource.Loading -> {
                    Log.d(TAG, "Loading")
                }
                is Resource.Success -> {
                    _userInfo.value = response.data ?: User()
                    Log.d(TAG, "dataView ${userInfo.value}")
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
}