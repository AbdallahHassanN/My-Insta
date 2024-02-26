package com.example.myinsta.useCases

import com.example.myinsta.repository.firebaseRepo.FirebaseRepository
import javax.inject.Inject

class FirebaseGetPostUseCase @Inject constructor(
    private val repo: FirebaseRepository
) {
    fun execute(postId: String) = repo.getPost(postId)
}