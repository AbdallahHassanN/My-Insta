package com.example.myinsta.presentation.addPostScreen

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.myinsta.common.isStoragePermissionGranted
import com.example.myinsta.common.pickFromGallery
import com.example.myinsta.components.BottomNavigation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddPostScreen(
    navController: NavController,
) {
    val context = LocalContext.current
    val isPermissionGranted = remember { mutableStateOf(false) }


    val getContent = rememberLauncherForActivityResult(
        contract = ActivityResultContracts
            .GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            /*TODO*/
        } else {
            navController.popBackStack()
        }
    }


    DisposableEffect(Unit) {
        // Request permission if not granted
        if (!isPermissionGranted.value) {
            isPermissionGranted.value = isStoragePermissionGranted(context)
        }
        onDispose { /* cleanup logic if needed */ }
    }


    isPermissionGranted.value = isStoragePermissionGranted(context)
    if (isPermissionGranted.value) {
        LaunchedEffect(true) {
            pickFromGallery(getContent)
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigation(navController = navController)
        }) {
    }

}

