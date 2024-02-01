package com.example.myinsta.presentation.feedScreen

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.myinsta.components.BottomNavigation
import com.example.myinsta.components.TopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FeedScreen(
    navController: NavController
) {
    Scaffold(
        topBar = { TopBar(navController = navController) },
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    ) {

    }
}