package com.example.myinsta.useCases

import com.example.myinsta.repository.FirebaseRepo.FirebaseRepository
import javax.inject.Inject

class FirebaseRemoveLikeUseCase @Inject constructor(
    private val repo: FirebaseRepository
) {
    fun execute(userId: String, postId: String, likeId: String) =
        repo.removeLike(userId, postId, likeId)
}