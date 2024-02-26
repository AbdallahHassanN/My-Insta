package com.example.myinsta.presentation.chatScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myinsta.common.Constants.TAG
import com.example.myinsta.presentation.feedScreen.addCommentScreen.CommentBox


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatScreen(
    chatRoomId: String,
    userId: String
) {
    val viewModel: ChatScreenViewModel = hiltViewModel()
    val currentUserId by viewModel.currentUserId.collectAsStateWithLifecycle()
    val currentUsername by viewModel.userName.collectAsStateWithLifecycle()
    val userInfo by viewModel.userInfo.collectAsStateWithLifecycle()

    Log.d(TAG, "room $chatRoomId")
    Log.d(TAG, "user $userId")
    LaunchedEffect(key1 = userId) {
        viewModel.getUserInfo(userId)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.Top
                ) {
                    item {
                        CommentBox(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth(),
                            label = "Message..."
                        ) {
                            viewModel.sendMessage(
                                senderId = currentUserId,
                                senderName = currentUsername,
                                receiverName = userInfo!!.username,
                                message = it,
                                chatId = chatRoomId,
                                receiverId = userInfo!!.id
                            )
                        }
                    }
                }
            }
        }
    }
}