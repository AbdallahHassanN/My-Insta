package com.example.myinsta.common

import android.annotation.SuppressLint
import android.content.Intent
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher

@SuppressLint("IntentReset")
fun pickFromGallery(getContent: ActivityResultLauncher<String>) {
    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    intent.type = "image/*"
    val mimetypes = arrayOf("image/jpeg", "image/png", "image/jpg")
    intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes)
    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    getContent.launch("image/*")
}