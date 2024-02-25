package com.example.myinsta.presentation.searchScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.myinsta.common.CircularProgressBar
import com.example.myinsta.common.Constants.TAG
import com.example.myinsta.components.SearchAppBar
import com.example.myinsta.components.BottomNavigation
import com.example.myinsta.common.UserList

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SearchScreen(
    navController: NavController,
) {
    val viewModel: SearchScreenViewModel = hiltViewModel()
    val usersByName = viewModel.usersByName.value
    val query by viewModel.query.collectAsStateWithLifecycle()
    val loading = viewModel.loading.value

    Scaffold(
        topBar = {
            SearchAppBar(
                query = query,
                onQueryChanged = { viewModel.onQueryChanged(it) },
                onExecuteSearch = { viewModel.getUsersByName(query) }
            )
        },
        bottomBar = {
            BottomNavigation(navController = navController)
        }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            UserList(
                users = usersByName,
                it = innerPadding,
                navController = navController,
                loading = loading
            )
            if (loading) {
                CircularProgressBar(isDisplayed = loading)
            }
            Log.d(TAG, usersByName.toString())
        }
    }
}