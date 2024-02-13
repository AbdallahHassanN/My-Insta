package com.example.myinsta.presentation.settingsScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myinsta.common.Constants.TAG
import com.example.myinsta.common.validator.EmptyValidator
import com.example.myinsta.common.validator.ValidateResult
import com.example.myinsta.models.User
import com.example.myinsta.response.Resource
import com.example.myinsta.useCases.FirebaseEditBio
import com.example.myinsta.useCases.FirebaseEditFullName
import com.example.myinsta.useCases.FirebaseEditUsername
import com.example.myinsta.useCases.FirebaseGetUserIdUseCase
import com.example.myinsta.useCases.FirebaseGetUserInfoUseCase
import com.example.myinsta.useCases.FirebaseLogoutUseCase
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    private val firebaseEditFullName: FirebaseEditFullName,
    private val firebaseEditBio: FirebaseEditBio,
    private val firebaseEditUsername: FirebaseEditUsername
) : ViewModel() {

    private val _userId = MutableStateFlow("")
    val userId = _userId.asStateFlow()

    private val _userInfo = MutableStateFlow<User?>(null)
    val userInfo = _userInfo.asStateFlow()

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()
    val usernameValidation = mutableStateOf<ValidateResult?>(null)

    private val _fullName = MutableStateFlow("")
    val fullName = _fullName.asStateFlow()
    private val _bio = MutableStateFlow("")
    val bio = _bio.asStateFlow()

    private val _editUsernameState = MutableStateFlow<Resource<Boolean>?>(null)
    val editUsernameState: StateFlow<Resource<Boolean>?> = _editUsernameState

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
        firebaseLogoutUseCase.execute().collect {
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
            firebaseEditFullName.execute(newName).collect { response ->
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

    fun changeUsername(newUsername: String) {
        viewModelScope.launch {
            usernameValidation.value = validateUsername()
            if (usernameValidation.value!!.isSuccess) {
                _editUsernameState.value = Resource.Loading(true)
                firebaseEditUsername.execute(
                    newUsername = newUsername.trim()
                ).collect {
                    _editUsernameState.value = it
                }
            }else{
                _editUsernameState.value = Resource.Error("Username Empty")
            }
        }
    }

    fun changeBio(newBio: String) {
        viewModelScope.launch {
            firebaseEditBio.execute(newBio).collect { response ->
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

    private fun validateUsername() = EmptyValidator(_username.value).validate()
    fun onUsernameChanged(newQuery: String) {
        _username.value = newQuery
    }

    fun onNameChanged(newQuery: String) {
        _fullName.value = newQuery
    }

    fun onBioChanged(newQuery: String) {
        _bio.value = newQuery
    }
}