package com.example.myinsta.presentation.settingsScreen

import android.net.Uri
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myinsta.common.CircularProgressBar
import com.example.myinsta.common.pickFromGallery
import com.example.myinsta.components.BoxItem
import com.example.myinsta.presentation.profileScreen.components.RoundedImageView
import com.example.myinsta.ui.theme.BigStone
import com.example.myinsta.ui.theme.QuickSandTypography
import com.example.navapp.Screens

@Composable
fun SettingsScreen(
    navController: NavController
) {
    val viewModel: SettingsScreenViewModel = hiltViewModel()
    val userId by viewModel.userId.collectAsStateWithLifecycle()
    val userInfo by viewModel.userInfo.collectAsStateWithLifecycle()
    val loading = viewModel.loading.value
    var username = ""
    var fullName = ""
    var img = ""

    LaunchedEffect(key1 = userId, block = {
        viewModel.getUserInfo(userId)
    })
    if (userInfo != null) {
        username = userInfo!!.username
        fullName = userInfo!!.fullName
        img = userInfo!!.imageUrl
    }

    val onLogoutClick: () -> Unit = {
        viewModel.logout()
        navController.navigate(Screens.MainScreen.route) {
            popUpTo(navController.graph.id) {
                inclusive = false
            }
        }
    }

    val getContent = rememberLauncherForActivityResult(
        contract = ActivityResultContracts
            .GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.updateUserProfilePhoto(uri)
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
            RoundedImageView(img)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            Text(
                text = fullName,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                style = QuickSandTypography.headlineLarge

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
                text = username,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                style = QuickSandTypography.labelSmall
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
                    },
                    color = Color.Black
                )
                BoxItem(
                    text = "Username",
                    onClick = {
                        navController.navigate(Screens.ChangeUsernameScreen.route)
                    },
                    color = Color.Black
                )
                BoxItem(
                    text = "Bio",
                    onClick = {
                        navController.navigate(Screens.ChangeBioScreen.route)
                    },
                    color = Color.Black
                )
                BoxItem(
                    text = "Profile Picture",
                    onClick = {
                        pickFromGallery(getContent)
                    },
                    color = Color.Black
                )
                BoxItem(
                    text = "Logout",
                    onClick = {
                        onLogoutClick()
                    },
                    color = Color.Red
                )
            }
            CircularProgressBar(isDisplayed = loading)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayInfoTF() {
    val navController = rememberNavController()
    SettingsScreen(navController)
}