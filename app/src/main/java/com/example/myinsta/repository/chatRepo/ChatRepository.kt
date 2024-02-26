package com.example.myinsta.repository.chatRepo

import com.example.myinsta.models.ChatUserList
import com.example.myinsta.models.User
import com.example.myinsta.response.Resource
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun sendMessage(
        senderId: String,
        senderUsername: String,
        receiverId: String,
        receiverUsername: String,
        message: String,
        chatId: String
    ): Flow<Resource<String>>
    fun getChats(userId: String): Flow<Resource<List<ChatUserList>>>
}