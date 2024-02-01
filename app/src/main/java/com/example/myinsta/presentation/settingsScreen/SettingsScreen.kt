package com.example.myinsta.presentation.settingsScreen

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myinsta.R
import com.example.myinsta.common.Constants.TAG
import com.example.myinsta.common.pickFromGallery
import com.example.myinsta.components.BoxItem
import com.example.myinsta.presentation.profileScreen.components.RoundedImageView
import com.example.myinsta.ui.theme.BigStone
import com.example.navapp.Screens

@Composable
fun SettingsScreen(
    navController: NavController
) {
    val getContent = rememberLauncherForActivityResult(
        contract = ActivityResultContracts
            .GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            /*TODO*/
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BigStone)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clickable {
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            RoundedImageView(painterResource(id = R.drawable.hakari))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            Text(
                text = "Account Name",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            Text(
                text = "UserName",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(20.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp) // Adjust the corner radius as needed
                )
                .align(Alignment.CenterHorizontally) // Adjust the corner radius as needed

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                BoxItem(
                    text = "Name",
                    onClick = {
                        navController.navigate(Screens.ChangeNameScreen.route)
                    }
                )
                BoxItem(text = "Username",
                    onClick = {
                        navController.navigate(Screens.ChangeUsernameScreen.route)
                    }
                )
                BoxItem(text = "Profile Picture",
                    onClick = {
                        pickFromGallery(getContent)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayInfoTF() {
    val navController = rememberNavController()
    SettingsScreen(navController)
}