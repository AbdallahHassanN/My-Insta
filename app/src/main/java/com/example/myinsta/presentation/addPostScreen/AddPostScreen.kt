package com.example.myinsta.presentation.addPostScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.myinsta.common.Constants.TAG
import com.example.myinsta.common.SearchAppBar
import com.example.myinsta.components.BottomNavigation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddPostScreen(
    navController: NavController,
) {

    Scaffold(
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    ) {

    }

}