package com.example.myinsta.presentation.feedScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myinsta.common.Constants
import com.example.myinsta.common.Constants.TAG
import com.example.myinsta.useCases.FirebaseGetUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FeedScreenViewModel
@Inject constructor(
    private val firebaseGetUserIdUseCase: FirebaseGetUserIdUseCase
) : ViewModel() {
    private val _userId = MutableStateFlow("")
    val userId = _userId.asStateFlow()
    init {
        firebaseGetUserIdUseCase.execute()?.let {
            _userId.value = it.uid
        }
    }
}