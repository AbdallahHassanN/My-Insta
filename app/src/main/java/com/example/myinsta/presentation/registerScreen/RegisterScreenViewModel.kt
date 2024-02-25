package com.example.myinsta.presentation.registerScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myinsta.common.Constants.TAG
import com.example.myinsta.common.validator.BaseValidator
import com.example.myinsta.common.validator.EmailValidator
import com.example.myinsta.common.validator.EmptyValidator
import com.example.myinsta.common.validator.ValidateResult
import com.example.myinsta.response.Resource
import com.example.myinsta.useCases.FirebaseRegisterUseCase
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterScreenViewModel @Inject constructor(
    private val firebaseRegisterUseCase: FirebaseRegisterUseCase
) : ViewModel() {

    private val _registerState = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val registerState: StateFlow<Resource<FirebaseUser>?> = _registerState

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()
    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()
    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()
    private val _fullName = MutableStateFlow("")
    val fullName = _fullName.asStateFlow()

    val emailValidation = mutableStateOf<ValidateResult?>(null)
    val passwordValidation = mutableStateOf<ValidateResult?>(null)
    val usernameValidation = mutableStateOf<ValidateResult?>(null)
    val fullNameValidation = mutableStateOf<ValidateResult?>(null)
    val loading = mutableStateOf(false)
    fun register() = viewModelScope.launch {
        emailValidation.value = validateEmail()
        fullNameValidation.value = validateFullName()
        passwordValidation.value = validatePassword()
        usernameValidation.value = validateUsername()

        if (emailValidation.value!!.isSuccess &&
            passwordValidation.value!!.isSuccess &&
            fullNameValidation.value!!.isSuccess
        ) {
            _registerState.value = Resource.Loading(true)
            loading.value = true
            firebaseRegisterUseCase.execute(
                email = _email.value.trim(),
                password = _password.value.trim(),
                username = _username.value.trim(),
                fullName = _fullName.value.trim()
            ).collect {
                _registerState.value = it
                Log.d(TAG, "View Model state value ${_registerState.value}")
                loading.value = false
            }
        }
    }

    private fun validateEmail() = BaseValidator.validate(
        EmptyValidator(_email.value), EmailValidator(_email.value.trim())
    )
    private fun validateFullName() = EmptyValidator(_fullName.value).validate()
    private fun validateUsername() = EmptyValidator(_username.value).validate()

    private fun validatePassword() = EmptyValidator(_password.value).validate()

    fun onEmailChanged(newQuery: String) {
        _email.value = newQuery
    }
    fun onPasswordChanged(newQuery: String) {
        _password.value = newQuery
    }
    fun onFullNameChanged(newQuery: String) {
        _fullName.value = newQuery
    }
    fun onUsernameChanged(newQuery: String) {
        _username.value = newQuery
    }
}