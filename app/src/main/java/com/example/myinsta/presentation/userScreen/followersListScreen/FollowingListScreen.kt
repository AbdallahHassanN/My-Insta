package com.example.myinsta.presentation.userScreen.followersListScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.myinsta.presentation.userScreen.UserScreenViewModel
import com.example.myinsta.ui.theme.QuickSandTypography
import com.example.myinsta.common.UserList

@SuppressLint("StateFlowValueCalledInComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FollowingListScreen(
    id:String,
    navController: NavController
) {
    val viewModel: UserScreenViewModel = hiltViewModel()
    val followingIdsList by viewModel.followingIdsList.collectAsStateWithLifecycle()
    val followingList by viewModel.followingList.collectAsStateWithLifecycle()
    val loading = viewModel.loading.value

    LaunchedEffect(key1 = id, block = {
        viewModel.getFollowingListIds(id)
    })
    viewModel.getFollowingList(followingIdsList)

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar ={
            Text(
                text = "Following",
                color = Color.Black,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(0.75F),
                style = QuickSandTypography.titleLarge
            )
        }
    ) {
        UserList(
            users = followingList,
            it = it,
            navController = navController,
            loading = loading
        )
    }
}