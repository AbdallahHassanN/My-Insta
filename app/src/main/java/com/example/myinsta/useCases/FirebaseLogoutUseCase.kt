package com.example.myinsta.useCases

import com.example.myinsta.repository.authRepo.AuthRepository
import javax.inject.Inject

class FirebaseLogoutUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    suspend fun execute() =
        repo.logout()
}