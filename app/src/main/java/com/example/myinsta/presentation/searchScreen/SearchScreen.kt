package com.example.myinsta.presentation.searchScreen

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.myinsta.common.SearchAppBar
import com.example.myinsta.components.BottomNavigation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    navController: NavController,
) {

    Scaffold(
        topBar = {
            SearchAppBar()
        },
        bottomBar = {
            BottomNavigation(navController = navController)
        }) {
    }
}