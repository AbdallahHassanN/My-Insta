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
import androidx.navigation.NavController
import com.example.myinsta.models.User
import com.example.navapp.Screens
import com.example.recipescompose.presentation.components.UserCard
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UserList(
    users: List<User>?,
    it: PaddingValues,
    navController: NavController,
    loading: Boolean
    ) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val currentUserId = currentUser?.uid ?: ""
    val listState = rememberLazyListState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(it)
    ) {
        LazyColumn(
            state = listState
        ) {
            itemsIndexed(
                items = users ?: emptyList()
            ) { index, user ->
                UserCard(user = user, onClick ={
                    if(currentUserId == user.id) {
                        navController.navigate(Screens.ProfileScreen.route)
                    }else{
                        navController.navigate(Screens.UserScreen.withArgs(user.id))
                    }
                })
            }
        }
    }
}