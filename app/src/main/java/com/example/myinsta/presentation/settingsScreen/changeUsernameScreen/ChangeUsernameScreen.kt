package com.example.myinsta.presentation.settingsScreen.changeNameScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.myinsta.components.ChangeNameInfo
import com.example.myinsta.presentation.settingsScreen.SettingsScreenViewModel
import com.example.navapp.Screens

@Composable
fun ChangeUsernameScreen(
    navController: NavController
) {
    val viewModel: SettingsScreenViewModel = hiltViewModel()
    val userId by viewModel.userId.collectAsStateWithLifecycle()
    var username = ""
    LaunchedEffect(key1 = userId, block = {
        viewModel.getUserInfo(userId)
    })
    ChangeNameInfo(
        pageText = "Username",
        labelText = "Username",
        text = username,
        onClick = {
            /*TODO Change The userName*/
            //viewModel.changeName(username)
            navController.navigate(Screens.SettingsScreen.route)
        },
        onTextChanged = { newUsername ->
            username = newUsername
        }
    )
}