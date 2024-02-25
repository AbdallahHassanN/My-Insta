package com.example.myinsta.presentation.feedScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.myinsta.common.ColumnPostCard
import com.example.myinsta.common.Constants
import com.example.myinsta.common.Constants.TAG
import com.example.myinsta.components.BottomNavigation
import com.example.myinsta.components.TopBar
import com.example.myinsta.components.UnseenStoryIcon
import com.example.myinsta.presentation.feedScreen.addCommentScreen.CommentItem
import com.example.navapp.Screens

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FeedScreen(
    navController: NavController
) {
    val viewModel: FeedScreenViewModel = hiltViewModel()
    val posts by viewModel.postsList.collectAsStateWithLifecycle()
    val currentUserId by viewModel.currentUserId.collectAsStateWithLifecycle()
    val postData by viewModel.postData.collectAsStateWithLifecycle()
    val userInfo by viewModel.userInfo.collectAsStateWithLifecycle()
    val isLoading by viewModel.loading.collectAsState()

    LaunchedEffect(key1 = true, block = {
        viewModel.getUsersDataOfFollowing()
    })

    LaunchedEffect(key1 = posts) {
        posts.forEach { post ->
            viewModel.getInfoByUserInfo(post!!.userId)
        }
    }

    Log.d(TAG, "postss $posts")
    Log.d(TAG, "posts $postData")


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
            LazyColumn(
                verticalArrangement = Arrangement.Top,
            ) {
                if (!(isLoading)) {
                    items(posts, key = { posts -> posts!!.postId }) { post ->
                        val user = userInfo[post!!.userId]
                        ColumnPostCard(
                            post = post,
                            onClick = {
                                navController
                                    .navigate(Screens.UserScreen.withArgs(post.userId))
                            },
                            navClick = {
                                navController
                                    .navigate(Screens.AddCommentScreen.withArgs(post.postId))
                            },
                            onLikeClick = {
                                if (!(post.likes.contains(currentUserId))) {
                                    viewModel.addLike(
                                        postId = post.postId,
                                        userId = currentUserId,
                                        userName = viewModel.userName.value
                                    )
                                } else {
                                    viewModel.removeLike(
                                        postId = post.postId,
                                        userId = currentUserId,
                                        likeId = currentUserId
                                    )
                                }
                            },
                            isLiked = post.likes.contains(currentUserId),
                            img = user?.imageUrl ?: "",
                        )
                    }
                }
            }
        }
    }
}