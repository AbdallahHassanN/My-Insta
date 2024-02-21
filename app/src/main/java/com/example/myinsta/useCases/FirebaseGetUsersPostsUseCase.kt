package com.example.myinsta.useCases

import com.example.myinsta.repository.FirebaseRepo.FirebaseRepository
import javax.inject.Inject

class FirebaseGetUsersPostsUseCase @Inject constructor(
    private val repo: FirebaseRepository
) {
    fun execute(
        posts: List<String>
    ) = repo.getUsersPosts(posts)
}