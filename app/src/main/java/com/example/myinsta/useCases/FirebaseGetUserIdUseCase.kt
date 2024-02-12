package com.example.myinsta.useCases

import com.example.myinsta.repository.authRepo.AuthRepository
import javax.inject.Inject

class FirebaseGetUserIdUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    fun execute() =
        repo.getUserId()
}