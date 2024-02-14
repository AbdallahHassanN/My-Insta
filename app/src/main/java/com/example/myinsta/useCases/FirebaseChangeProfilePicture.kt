package com.example.myinsta.useCases

import android.content.Context
import android.net.Uri
import com.example.myinsta.repository.authRepo.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FirebaseChangeProfilePicture @Inject constructor(
    private val repo: AuthRepository,
    @ApplicationContext private val context: Context
) {
    fun execute(uri: Uri, path: String) =repo.updateUserImg(uri, path)
}