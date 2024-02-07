package com.example.myinsta.presentation.chatScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myinsta.components.ChatItem
import com.example.myinsta.ui.theme.QuickSandTypography

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(
            text = "Messages",
            modifier = Modifier
                .padding( 10.dp),
            fontSize = 20.sp,
            style = QuickSandTypography.headlineLarge
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(100) {
                ChatItem()
            }
        }
    }
}