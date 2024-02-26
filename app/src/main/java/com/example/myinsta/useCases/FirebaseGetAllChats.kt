package com.example.myinsta.useCases

import com.example.myinsta.repository.chatRepo.ChatRepository
import javax.inject.Inject

class FirebaseGetAllChats @Inject constructor(
    private val repo: ChatRepository
) {
    fun execute(userId: String) = repo.getChats(userId)
}