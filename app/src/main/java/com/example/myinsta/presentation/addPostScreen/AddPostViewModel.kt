package com.example.myinsta.presentation.addPostScreen

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myinsta.common.Constants
import com.example.myinsta.response.Resource
import com.example.myinsta.useCases.FirebaseCreatePostUseCase
import com.example.myinsta.useCases.FirebaseGetUserByName
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel

class AddPostViewModel
@Inject constructor(
    private val firebaseCreatePostUseCase: FirebaseCreatePostUseCase
) : ViewModel() {

    private val _caption = MutableStateFlow("")
    val caption = _caption.asStateFlow()
    private val _postState = MutableStateFlow<Resource<Boolean>?>(null)
    val postState: StateFlow<Resource<Boolean>?> = _postState
    fun createPost(
        postDescription: String,
        userId: String,
        userName: String,
        imageUrl:Uri
    ) {
        viewModelScope.launch {
            val postId = UUID.randomUUID().toString()
            firebaseCreatePostUseCase
                .execute(
                    postId = postId,
                    postDescription = postDescription,
                    userId = userId,
                    userName = userName,
                    imageUrl = imageUrl
                ).collect { response ->
                    when (response) {
                        is Resource.Error -> {
                            Log.d(Constants.TAG, "Error response")
                        }

                        is Resource.Loading -> {
                            _postState.value = Resource.Loading()
                            Log.d(Constants.TAG, "Loading")
                        }

                        is Resource.Success -> {
                            _postState.value = response
                            Log.d(Constants.TAG, "Success")
                        }
                    }
                }
        }
    }

    fun onCaptionChanged(newQuery: String) {
        _caption.value = newQuery
    }
}