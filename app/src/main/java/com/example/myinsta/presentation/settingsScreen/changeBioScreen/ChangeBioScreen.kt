package com.example.myinsta.presentation.settingsScreen.changeBioScreen

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
fun ChangeBioScreen(
    navController: NavController
) {
    val viewModel: SettingsScreenViewModel = hiltViewModel()
    val bioValue by viewModel.bio.collectAsStateWithLifecycle()

    ChangeNameInfo(
        pageText = "Edit Bio",
        labelText = "Bio",
        text = bioValue,
        onClick = {
            viewModel.changeBio(bioValue)
            navController.navigate(Screens.SettingsScreen.route) {
                popUpTo(Screens.SettingsScreen.route) { inclusive = true }
            }
        },
        onTextChanged = {
            viewModel.onBioChanged(it)
        }
    )
}