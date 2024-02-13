package com.example.myinsta.useCases

import com.example.myinsta.repository.authRepo.AuthRepository
import javax.inject.Inject

class FirebaseEditUsername  @Inject constructor(
    private val repo: AuthRepository
) {
    fun execute(newUsername:String) = repo.changeUsername(newUsername)
}