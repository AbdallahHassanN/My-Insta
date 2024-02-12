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
fun ChangeNameScreen(
    navController: NavController
) {
    val viewModel: SettingsScreenViewModel = hiltViewModel()
    val userId by viewModel.userId.collectAsStateWithLifecycle()
    var fullName = ""

    LaunchedEffect(key1 = userId, block = {
        viewModel.getUserInfo(userId)
    })
    ChangeNameInfo(
        pageText = "Name",
        labelText = "Name",
        text = fullName,
        onClick = {
            viewModel.changeName(fullName)
            navController.navigate(Screens.SettingsScreen.route)
        },
        onTextChanged = { newName ->
            fullName = newName
        }
    )
}