package com.example.myinsta.presentation.settingsScreen.changeNameScreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.myinsta.components.ChangeNameInfo
import com.example.navapp.Screens

@Composable
fun ChangeNameScreen(
    navController: NavController
) {
    ChangeNameInfo(
        pageText = "Name",
        labelText = "Name",
        text = "Account Name",
        onClick = {
        /*TODO Checks*/
        navController.navigate(Screens.SettingsScreen.route)
    })
}