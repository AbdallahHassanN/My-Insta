package com.example.myinsta.presentation.feedScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.myinsta.common.Constants.TAG
import com.example.myinsta.components.BottomNavigation
import com.example.myinsta.components.PostOnFeed
import com.example.myinsta.components.TopBar
import com.example.myinsta.components.UnseenStoryIcon
import com.example.myinsta.presentation.registerScreen.RegisterScreenViewModel

@Composable
fun FeedScreen(
    navController: NavController
) {
    val viewModel: FeedScreenViewModel = hiltViewModel()
    val userId by viewModel.userId.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true, block = {
        Log.d(TAG, userId)
    })

    Scaffold(
        topBar = { TopBar(navController = navController) },
        bottomBar = { BottomNavigation(navController = navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                LazyRow(
                    Modifier.padding(start = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(20) {
                        UnseenStoryIcon()
                    }
                }
            }
            items(5) {
                PostOnFeed()
            }
        }
    }
}