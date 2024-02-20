package com.example.myinsta.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.myinsta.models.Post
import com.example.navapp.Screens
import com.example.recipescompose.presentation.components.UserCard
import com.google.firebase.auth.FirebaseAuth

@Composable
fun GridPostsList(
    posts: List<Post>?,
    navController: NavController,
    loading: Boolean
) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val currentUserId = currentUser?.uid ?: ""
    val listState = rememberLazyListState()
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
        ) {
            itemsIndexed(
                items = posts ?: emptyList()
            ) { index, post ->
                GridPostsCard(
                    post = post,
                    onClick = {}
                )
            }
        }
    }
}