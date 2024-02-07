package com.example.myinsta.presentation.feedScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myinsta.components.BottomNavigation
import com.example.myinsta.components.PostOnFeed
import com.example.myinsta.components.TopBar
import com.example.myinsta.components.UnseenStoryIcon

@Composable
fun FeedScreen(
    navController: NavController
) {
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