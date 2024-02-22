package com.example.myinsta.presentation.feedScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.example.myinsta.common.ColumnPostList
import com.example.myinsta.components.BottomNavigation
import com.example.myinsta.components.TopBar
import com.example.myinsta.components.UnseenStoryIcon

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FeedScreen(
    navController: NavController
) {
    val viewModel: FeedScreenViewModel = hiltViewModel()
    val posts by viewModel.postsList.collectAsStateWithLifecycle()
    val currentUserId by viewModel.currentUserId.collectAsStateWithLifecycle()
    val postData by viewModel.postData.collectAsStateWithLifecycle()

    val loading = viewModel.loading.value

    LaunchedEffect(key1 = true, block = {
        viewModel.getUsersDataOfFollowing()
    })
    val onLikeClick: (postId: String) -> Unit = { postId ->
        viewModel.getPost(postId)
        if (postData?.likes?.contains(currentUserId) == true) {
            viewModel.removeLike(
                postId = postId,
                userId = currentUserId,
                likeId = currentUserId
            )
        } else {
            viewModel.addLike(
                postId = postId,
                userId = currentUserId,
                userName = viewModel.userName.value
            )
        }
    }
    Scaffold(
        topBar = { TopBar(navController = navController) },
        bottomBar = { BottomNavigation(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyRow(
                Modifier.padding(start = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(20) {
                    UnseenStoryIcon()
                }
            }
            ColumnPostList(
                posts = posts,
                navController = navController,
                loading = loading,
                it = paddingValues,
                onLikeClick = onLikeClick,
                currentUserId = currentUserId, // Pass currentUserId to ColumnPostList
            )
        }
    }
}