package com.example.myinsta.presentation.feedScreen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.myinsta.common.Constants.TAG
import com.example.myinsta.common.ColumnPostList
import com.example.myinsta.common.ScrollDirection
import com.example.myinsta.common.validator.ValidateResult
import com.example.myinsta.components.BottomNavigation
import com.example.myinsta.components.TopBar
import com.example.myinsta.components.UnseenStoryIcon
import kotlinx.coroutines.flow.MutableStateFlow

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FeedScreen(
    navController: NavController
) {
    val viewModel: FeedScreenViewModel = hiltViewModel()
    val posts by viewModel.postsList.collectAsStateWithLifecycle()
    val users by viewModel.followingList.collectAsStateWithLifecycle()
    val commentValue by viewModel.commentValue.collectAsStateWithLifecycle()

    val loading = viewModel.loading.value

    LaunchedEffect(key1 = true, block = {
        viewModel.getUsersDataOfFollowing()
    })


    Scaffold(
        topBar = { TopBar(navController = navController) },
        bottomBar = { BottomNavigation(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyRow(
                Modifier.padding(start = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(20) {
                    UnseenStoryIcon()
                }
            }
            ColumnPostList(
                posts = posts,
                navController = navController,
                loading = loading,
                it = paddingValues,
                inputValue = commentValue,
                onQueryChanged = { viewModel.onCommentChanged(it) },
                isError = viewModel.commentValidation.value?.isSuccess?.not() ?: false,
            )
        }
    }
}