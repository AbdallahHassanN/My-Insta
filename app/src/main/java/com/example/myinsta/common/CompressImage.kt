package com.example.myinsta.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

fun compressImage(
    uri: Uri,
    context: Context
)
        : Uri {
    val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
    val compressedImageFile = File(context.cacheDir, "temp_image.jpg")
    val compressedOutputStream = FileOutputStream(compressedImageFile)
    compressedOutputStream.write(outputStream.toByteArray())
    compressedOutputStream.close()
    return Uri.fromFile(compressedImageFile)
}
suspend fun uploadCompressedImage(
    compressedImageUri: Uri,
    path: String,
    storage: FirebaseStorage
)
        : Boolean {
    return try {
        storage.getReference(path).putFile(compressedImageUri).await()
        true
    } catch (e: Exception) {
        false
    }
}