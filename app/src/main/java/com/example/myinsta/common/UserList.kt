package com.example.recipescompose.presentation.components

import android.util.Log
import androidx.compose.foundation.background
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
import com.example.myinsta.common.Constants.TAG
import com.example.myinsta.models.User
import com.example.myinsta.ui.theme.Purple80
import com.example.navapp.Screens

@Composable
fun UserList(
    users: List<User>?,
    it: PaddingValues,
    navController: NavController,
    loading: Boolean
    ) {

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
                    navController.navigate(Screens.ProfileScreen.withArgs(user.id))
                })
            }
        }
    }
}