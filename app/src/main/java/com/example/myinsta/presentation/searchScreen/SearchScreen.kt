package com.example.myinsta.presentation.searchScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
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
                onQueryChanged = {viewModel.onQueryChanged(it)},
                onExecuteSearch = {viewModel.getUsersByName(query)}
            )
        },
        bottomBar = {
            BottomNavigation(navController = navController)
        }) { it ->
        UserList(
            users = usersByName,
            it = it,
            navController = navController,
            loading = loading
        )
        Log.d(TAG,usersByName.toString())
    }
}