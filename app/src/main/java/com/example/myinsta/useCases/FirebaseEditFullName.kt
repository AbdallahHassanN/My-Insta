package com.example.myinsta.useCases

import com.example.myinsta.repository.authRepo.AuthRepository
import javax.inject.Inject

class FirebaseEditFullName  @Inject constructor(
    private val repo: AuthRepository
) {
    fun execute(newName:String) = repo.changeName(newName)
}