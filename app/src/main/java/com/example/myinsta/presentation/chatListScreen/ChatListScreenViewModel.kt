package com.example.myinsta.presentation.chatListScreen

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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListScreenViewModel
@Inject constructor(
    private val firebaseGetUserIdUseCase: FirebaseGetUserIdUseCase,
    private val firebaseGetUserInfoUseCase: FirebaseGetUserInfoUseCase,
    private val firebaseGetAllChats: FirebaseGetAllChats
) : ViewModel() {
    private val _currentUserId = MutableStateFlow<String?>(null)
    private val _chatList = MutableStateFlow<List<ChatUserList>>(emptyList())
    val chatList = _chatList.asStateFlow()
    private val _userInfo = MutableStateFlow<User?>(null)
    val userInfo = _userInfo.asStateFlow()

    val loading = mutableStateOf(false)

    init {
        firebaseGetUserIdUseCase.execute()?.let { user ->
            _currentUserId.value = user.uid
            getChats(_currentUserId.value!!)
        }
    }

    fun getUserInfo(id: String) = viewModelScope.launch {
        firebaseGetUserInfoUseCase.execute(
            userId = id
        ).catch {
            Log.d(Constants.TAG, "Error ${it.message}")
        }.collect { response ->
            when (response) {
                is Resource.Error -> {
                    Log.d(Constants.TAG, "Error response")
                }

                is Resource.Loading -> {
                    Log.d(Constants.TAG, "Loading")
                }

                is Resource.Success -> {
                    _userInfo.value = response.data ?: User()
                }
            }
        }
    }

    fun getChats(userId: String) {
        viewModelScope.launch {
            firebaseGetAllChats.execute(userId).collect { response ->
                when (response) {
                    is Resource.Error -> {
                        loading.value = false
                        Log.d(TAG,"chat list Error")
                    }
                    is Resource.Loading -> {
                        loading.value = true
                    }
                    is Resource.Success -> {
                        response.data?.let {
                            val chats = it.sortedByDescending {
                                it.lastMessageTime
                            }
                            _chatList.value = chats
                            loading.value = false
                        }
                    }
                }
            }
        }
    }
}