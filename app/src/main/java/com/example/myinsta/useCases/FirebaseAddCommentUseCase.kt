package com.example.myinsta.useCases

import com.example.myinsta.repository.FirebaseRepo.FirebaseRepository
import javax.inject.Inject

class FirebaseAddCommentUseCase @Inject constructor(
    private val repo: FirebaseRepository
) {
    fun execute(
        postId: String,
        content: String,
        authorId: String,
        authorName: String
    ) =
        repo.addComment(postId, content, authorId, authorName)
}