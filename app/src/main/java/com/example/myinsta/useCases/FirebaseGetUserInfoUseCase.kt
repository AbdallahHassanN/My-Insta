package com.example.myinsta.useCases

import com.example.myinsta.repository.AuthRepository
import javax.inject.Inject

class FirebaseGetUserInfoUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    fun execute(userId: String) =
        repo.getUserInfo(userId = userId)
}