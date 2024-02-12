package com.example.myinsta.presentation.settingsScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myinsta.common.Constants.TAG
import com.example.myinsta.models.User
import com.example.myinsta.response.Resource
import com.example.myinsta.useCases.FirebaseEditFullName
import com.example.myinsta.useCases.FirebaseGetUserIdUseCase
import com.example.myinsta.useCases.FirebaseGetUserInfoUseCase
import com.example.myinsta.useCases.FirebaseLogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel
@Inject constructor(
    private val firebaseGetUserIdUseCase: FirebaseGetUserIdUseCase,
    private val firebaseGetUserInfoUseCase: FirebaseGetUserInfoUseCase,
    private val firebaseLogoutUseCase: FirebaseLogoutUseCase,
    private val firebaseEditFullName: FirebaseEditFullName
) : ViewModel() {

    private val _userId = MutableStateFlow("")
    val userId = _userId.asStateFlow()

    private val _userInfo = MutableStateFlow<User?>(null)
    val userInfo = _userInfo.asStateFlow()

    init {
        firebaseGetUserIdUseCase.execute()?.let {
            _userId.value = it.uid
            getUserInfo(_userId.value)
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
                    _userInfo.value = response.data!!
                    Log.d(TAG, "dataView ${userInfo.value}")
                }
            }
        }
    }

    fun logout() = viewModelScope.launch {
        firebaseLogoutUseCase.execute()
            .collect {
                when (it) {
                    is Resource.Error -> {
                        Log.d(TAG, "Logout Failed")
                    }

                    is Resource.Loading -> {
                        Log.d(TAG, "Logout Loading")
                    }

                    is Resource.Success -> {
                        Log.d(TAG, "Logout Success")
                    }
                }
            }
    }

    fun changeName(newName: String) {
        viewModelScope.launch {
            firebaseEditFullName
                .execute(newName)
                .collect { response ->
                    when (response) {
                        is Resource.Error -> {
                            Log.d(TAG, "Error response")
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