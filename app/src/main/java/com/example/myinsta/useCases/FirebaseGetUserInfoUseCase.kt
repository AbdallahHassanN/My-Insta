package com.example.myinsta.useCases

import android.content.Context
import com.example.myinsta.repository.authRepo.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FirebaseGetUserInfoUseCase @Inject constructor(
    private val repo: AuthRepository,
    @ApplicationContext private val context: Context
) {
    fun execute(userId: String) =
        repo.getUserInfo(userId = userId)
}