package com.example.myinsta.presentation.notificationScreen

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
import androidx.navigation.NavController
import com.example.myinsta.components.NotificationItem
import com.example.myinsta.ui.theme.QuickSandTypography

@Composable
fun NotificationScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(
            text = "Notifications",
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
                NotificationItem()
            }
        }
    }
}