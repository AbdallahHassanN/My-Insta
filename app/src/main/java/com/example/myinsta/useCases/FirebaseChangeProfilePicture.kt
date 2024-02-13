package com.example.myinsta.useCases

import android.net.Uri
import com.example.myinsta.repository.authRepo.AuthRepository
import javax.inject.Inject

class FirebaseChangeProfilePicture @Inject constructor(
    private val repo: AuthRepository
) {
    fun execute(uri: Uri, path: String) =repo.updateUserImg(uri, path)
}