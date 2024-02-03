package com.example.myinsta.presentation.notificationScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myinsta.R
import com.example.myinsta.components.NotificationItem
import com.example.myinsta.components.PostOnFeed
import com.example.myinsta.components.UnseenStoryIcon
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