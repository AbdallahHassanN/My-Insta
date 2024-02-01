package com.example.myinsta.common

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myinsta.common.Constants.PERMISSIONS_REQUEST_READ_STORAGE

fun isStoragePermissionGranted(context: Context): Boolean {
    if (
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        != PackageManager.PERMISSION_GRANTED

    ) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            PERMISSIONS_REQUEST_READ_STORAGE
        )
        return false
    } else {
        return true
    }
}
