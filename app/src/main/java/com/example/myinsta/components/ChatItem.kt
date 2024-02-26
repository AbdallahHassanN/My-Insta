package com.example.myinsta.components

import android.util.Log
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
import com.example.myinsta.common.UserChatCard
import com.example.myinsta.models.ChatUserList
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ChatItem(
    users: List<ChatUserList>?,
    it: PaddingValues,
    navController: NavController,
    loading: Boolean,
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
                items = users ?: emptyList(),
            ) { _, chatItem ->
                UserChatCard(chatItem = chatItem,
                    onClick = {
                  Log.d(TAG, "chat user ${chatItem.friendUsername}")
                })
            }
        }
    }
}