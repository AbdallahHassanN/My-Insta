package com.example.myinsta.presentation.chatListScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.myinsta.components.ChatItem
import com.example.myinsta.ui.theme.QuickSandTypography

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatListScreen(
    navController: NavController
) {
    val viewModel: ChatListScreenViewModel = hiltViewModel()
    val chatList by viewModel.chatList.collectAsStateWithLifecycle()
    val loading = viewModel.loading.value
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Text(
                text = "Messages",
                color = Color.Black,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(0.75F),
                style = QuickSandTypography.titleLarge
            )
        }
    ) {
        if (chatList.isNotEmpty()) {
            ChatItem(
                users = chatList,
                it = it,
                navController = navController,
                loading = loading,
            )
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No Active Chats"
                )
            }
        }
    }
}