package com.example.myinsta.useCases

import com.example.myinsta.repository.chatRepo.ChatRepository
import javax.inject.Inject

class FirebaseSendMessage
@Inject constructor(
    private val repo: ChatRepository
) {

    fun execute(
        senderId: String,
        senderUsername: String,
        receiverId: String,
        receiverUsername: String,
        message: String,
        chatId: String
    ) =
        repo
            .sendMessage(
                senderId = senderId,
                senderUsername = senderUsername,
                receiverId = receiverId,
                receiverUsername = receiverUsername,
                message = message,
                chatId = chatId
            )
}