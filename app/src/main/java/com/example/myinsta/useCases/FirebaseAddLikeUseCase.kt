package com.example.myinsta.useCases

import com.example.myinsta.repository.firebaseRepo.FirebaseRepository
import javax.inject.Inject

class FirebaseAddLikeUseCase @Inject constructor(
    private val repo: FirebaseRepository
) {
    fun execute(
        postId: String,
        userId: String,
        userName: String
    ) =
        repo.addLike(postId, userId, userName)
}