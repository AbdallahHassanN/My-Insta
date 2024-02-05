package com.example.myinsta.useCases

import com.example.myinsta.repository.AuthRepository
import javax.inject.Inject

class FirebaseLogoutUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    suspend fun execute() =
        repo.logout()
}