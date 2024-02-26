package com.example.myinsta.presentation.chatScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myinsta.common.Constants
import com.example.myinsta.common.Constants.TAG
import com.example.myinsta.models.ChatUserList
import com.example.myinsta.models.User
import com.example.myinsta.response.Resource
import com.example.myinsta.useCases.FirebaseGetAllChats
import com.example.myinsta.useCases.FirebaseGetFollowingInfoUseCase
import com.example.myinsta.useCases.FirebaseGetFollowingListIds
import com.example.myinsta.useCases.FirebaseGetUserIdUseCase
import com.example.myinsta.useCases.FirebaseGetUserInfoUseCase
import com.example.myinsta.useCases.FirebaseSendMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatScreenViewModel
@Inject constructor(
    private val firebaseSendMessage: FirebaseSendMessage,
    private val firebaseGetUserIdUseCase: FirebaseGetUserIdUseCase,
    private val firebaseGetUserInfoUseCase: FirebaseGetUserInfoUseCase,
) : ViewModel() {

    private val _currentUserId = MutableStateFlow<String>("")
    val currentUserId = _currentUserId.asStateFlow()
    private val _userName = MutableStateFlow<String>("")
    val userName = _userName.asStateFlow()
    private val _userInfo = MutableStateFlow<User?>(null)
    val userInfo = _userInfo.asStateFlow()

    val loading = mutableStateOf(false)

    init {
        firebaseGetUserIdUseCase.execute()?.let { user ->
            _currentUserId.value = user.uid
            _userName.value = user.displayName.toString()
        }
    }

    fun sendMessage(
        senderId: String,
        senderName: String,
        receiverId: String,
        receiverName: String,
        message: String,
        chatId: String
    ) {
        viewModelScope.launch {
            firebaseSendMessage.execute(
                senderId,
                senderName,
                receiverId,
                receiverName,
                message,
                chatId
            ).collect { response ->
                when (response) {
                    is Resource.Error -> {
                        loading.value = false
                        Log.d(TAG, "Sending failed")
                    }

                    is Resource.Loading -> {
                        loading.value = true
                    }

                    is Resource.Success -> {
                        response.data?.let {
                            Log.d(TAG, "Sent")
                        }
                    }
                }
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
}