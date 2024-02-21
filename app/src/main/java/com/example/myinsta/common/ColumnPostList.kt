package com.example.myinsta.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myinsta.models.Post
import com.example.navapp.Screens


@Composable
fun ColumnPostList(
    loading: Boolean,
    posts: List<Post?>,
    navController: NavController,
    it: PaddingValues,
) {
    val listState = rememberLazyListState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        LazyColumn(
            state = listState
        ) {
            itemsIndexed(
                items = posts,
            ) { _, post ->
                ColumnPostCard(
                    post = post!!,
                    onClick = {
                        navController.navigate(Screens.UserScreen.withArgs(post.userId))
                    },
                    navClick = {
                        navController.navigate(Screens.AddCommentScreen.withArgs(post.postId))
                    },
                )
            }
        }
    }
}