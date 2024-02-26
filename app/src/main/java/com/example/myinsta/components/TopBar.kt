package com.example.myinsta.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myinsta.ui.theme.QuickSandTypography
import com.example.navapp.Screens

@Composable
fun TopBar(
    navController: NavController
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = Color.White,
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Text(
                    text = "MyInsta",
                    color = Color.Black,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(0.75F),
                    style = QuickSandTypography.titleLarge
                )

                IconButton(
                    onClick = {
                        navController.navigate(Screens.NotificationScreen.route)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Filled.FavoriteBorder,
                        contentDescription = "Search",
                        tint = Color.Black
                    )
                }
                IconButton(
                    onClick = {
                        navController.navigate(Screens.ChatListScreen.route)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Filled.MailOutline,
                        contentDescription = "Search",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}
