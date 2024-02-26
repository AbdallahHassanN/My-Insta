package com.example.myinsta.useCases

import com.example.myinsta.repository.firebaseRepo.FirebaseRepository
import javax.inject.Inject

class FirebaseGetCommentsUseCase  @Inject constructor(
    private val repo: FirebaseRepository
) {
    fun execute(postId: String) = repo.getPostComments(postId)
}