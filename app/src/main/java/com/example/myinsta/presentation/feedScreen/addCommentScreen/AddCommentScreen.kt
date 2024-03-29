package com.example.myinsta.presentation.feedScreen.addCommentScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.myinsta.R
import com.example.myinsta.common.Constants
import com.example.myinsta.common.Constants.TAG
import com.example.myinsta.components.CenteredText
import com.example.myinsta.models.Comment
import com.example.myinsta.presentation.feedScreen.FeedScreenViewModel

@Composable
fun AddCommentScreen(
    postId: String,
    navController: NavHostController
) {
    val viewModel: FeedScreenViewModel = hiltViewModel()
    val postData by viewModel.postData.collectAsStateWithLifecycle()
    val commentsList by viewModel.commentsList.collectAsStateWithLifecycle()
    val currentUserId by viewModel.currentUserId.collectAsStateWithLifecycle()
    val userInfo by viewModel.userInfo.collectAsStateWithLifecycle()
    val isLoading by viewModel.loading.collectAsState()

    LaunchedEffect(key1 = Unit) {
        postId.let {
            viewModel.getPost(postId)
            viewModel.getComments(postId)
        }
    }
    LaunchedEffect(key1 = commentsList) {
        commentsList.forEach { comment ->
            viewModel.getInfoByUserInfo(comment!!.userId)
            Log.d(TAG, "Screen $userInfo")
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            CenteredText(
                text = "Comments", color = Color.Gray, onClick = {}, size = 20.sp
            )
            Box(
                modifier = Modifier.weight(1f)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.Top
                ) {
                    if (!(isLoading)) {
                        items(commentsList) { comment ->
                            val user = userInfo[comment!!.userId]
                            CommentItem(
                                img = user?.imageUrl ?: "",
                                comment = comment
                            )
                        }
                    }
                    item {
                        CommentBox(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                        ) { comment ->
                            postData.let {
                                viewModel.addComment(
                                    postId = postId,
                                    comment = comment,
                                    userId = currentUserId,
                                    authorName = viewModel.userName.value,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}