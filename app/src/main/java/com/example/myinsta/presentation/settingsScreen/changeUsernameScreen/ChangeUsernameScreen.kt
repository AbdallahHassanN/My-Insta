package com.example.myinsta.presentation.settingsScreen.changeNameScreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.myinsta.components.ChangeNameInfo
import com.example.navapp.Screens

@Composable
fun ChangeUsernameScreen(
    navController: NavController
) {
    ChangeNameInfo(
        pageText = "Edit username",
        labelText = "UserName",
        text = "My Username",
        onClick = {
            /*TODO Checks*/
            navController.navigate(Screens.SettingsScreen.route)
        })
}