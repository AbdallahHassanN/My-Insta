package com.example.myinsta.useCases

import com.example.myinsta.repository.authRepo.AuthRepository
import javax.inject.Inject

class FirebaseEditBio  @Inject constructor(
    private val repo: AuthRepository
) {
    fun execute(newBio:String) = repo.changeBio(newBio)
}