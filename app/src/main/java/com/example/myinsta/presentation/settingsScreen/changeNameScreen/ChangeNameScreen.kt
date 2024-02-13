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
    val nameValue by viewModel.fullName.collectAsStateWithLifecycle()


    ChangeNameInfo(
        pageText = "Edit Name",
        labelText = "Name",
        text = nameValue,
        onClick = {
            viewModel.changeName(nameValue)
            navController.navigate(Screens.SettingsScreen.route)
        },
        onTextChanged = {
            viewModel.onNameChanged(it)
        }
    )
}