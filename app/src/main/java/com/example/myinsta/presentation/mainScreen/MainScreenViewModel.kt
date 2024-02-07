package com.example.myinsta.presentation.mainScreen

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
import com.example.myinsta.useCases.FirebaseLogoutUseCase
import com.example.myinsta.useCases.FirebaseSignInUseCase
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val firebaseSignInUseCase: FirebaseSignInUseCase,
    private val firebaseLogoutUseCase: FirebaseLogoutUseCase,
    ) : ViewModel() {

    private val _signInState = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signInState : StateFlow<Resource<FirebaseUser>?> = _signInState

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()
    val emailValidation = mutableStateOf<ValidateResult?>(null)
    val passwordValidation = mutableStateOf<ValidateResult?>(null)


    fun signIn() = viewModelScope.launch {
        _signInState.value = Resource.Loading()
        emailValidation.value = validateEmail()
        passwordValidation.value = validatePassword()

        if (emailValidation.value!!.isSuccess && passwordValidation.value!!.isSuccess) {
            firebaseSignInUseCase.execute(
                email = _email.value.trim(), password = _password.value.trim()
            ).collect {
                    _signInState.value = it
                Log.d(TAG,"View Model value ${_signInState.value}")
                }
        }
    }
    fun logout() = viewModelScope.launch {
        firebaseLogoutUseCase.execute()
            .collect {
            when (it) {
                is Resource.Error -> {
                    Log.d(TAG,"Logout Failed")
                }
                is Resource.Loading -> {
                    Log.d(TAG,"Logout Loading")
                }
                is Resource.Success -> {
                    _signInState.value = null
                }
            }

        }
    }
    private fun validateEmail() = BaseValidator.validate(
            EmptyValidator(_email.value), EmailValidator(_email.value.trim())
        )

    private fun validatePassword() = EmptyValidator(_password.value).validate()

    fun onEmailChanged(newQuery: String) {
        _email.value = newQuery
    }

    fun onPasswordChanged(newQuery: String) {
        _password.value = newQuery
    }

}