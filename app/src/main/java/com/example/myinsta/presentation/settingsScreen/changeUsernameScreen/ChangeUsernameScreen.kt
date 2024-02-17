package com.example.myinsta.presentation.settingsScreen.changeNameScreen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.myinsta.common.Constants
import com.example.myinsta.common.Constants.TAG
import com.example.myinsta.components.ChangeNameInfo
import com.example.myinsta.presentation.settingsScreen.SettingsScreenViewModel
import com.example.myinsta.response.Resource
import com.example.navapp.Screens

@Composable
fun ChangeUsernameScreen(
    navController: NavController
) {
    val viewModel: SettingsScreenViewModel = hiltViewModel()
    val usernameValue by viewModel.username.collectAsStateWithLifecycle()
    val editUsernameState = viewModel.editUsernameState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    // Function to show the snackBar
    suspend fun showSnackBar(message: String) {
        snackBarHostState.showSnackbar(message)
    }
    LaunchedEffect(true) {
        viewModel.editUsernameState.collect { state ->
            when (state) {
                is Resource.Error -> {
                    val error = state.message
                    showSnackBar(error.toString())
                    Log.d(TAG, "HA ? ${error.toString()}")
                }
                is Resource.Loading -> {
                    Log.d(TAG, "Loading")
                }
                is Resource.Success -> {
                    navController.navigate(Screens.SettingsScreen.route) {
                        popUpTo(Screens.SettingsScreen.route) { inclusive = true }
                    }
                }
                else -> {
                    Log.d(TAG, "Unexpected Error")
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ChangeNameInfo(
            pageText = "Edit Username",
            labelText = "Username",
            text = usernameValue,
            onClick = {
                viewModel.changeUsername(usernameValue)
            },
            onTextChanged = {
                viewModel.onUsernameChanged(it)
            }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp), // Adjust padding as needed
            contentAlignment = Alignment.BottomCenter
        ) {
            SnackbarHost(
                hostState = snackBarHostState,
            ) { snackBar ->
                Snackbar(
                    snackbarData = snackBar,
                    containerColor = Color.Red
                )
            }
        }
    }
}