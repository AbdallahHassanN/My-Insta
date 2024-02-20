package com.example.myinsta.presentation.addPostScreen.confirmPost

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.myinsta.R
import com.example.myinsta.common.Constants.DEFAULT_User_IMAGE
import com.example.myinsta.common.Constants.TAG
import com.example.myinsta.common.loadPicture
import com.example.myinsta.components.MyButton
import com.example.myinsta.components.UsernameOrEmailTextField
import com.example.myinsta.presentation.addPostScreen.AddPostViewModel
import com.example.myinsta.presentation.profileScreen.ProfileScreenViewModel
import com.example.myinsta.presentation.profileScreen.components.RoundedImageView
import com.example.myinsta.presentation.registerScreen.RegisterScreenViewModel
import com.example.myinsta.response.Resource
import com.example.myinsta.ui.theme.QuickSandTypography
import com.example.navapp.Screens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ConfirmPostScreen(
    imagePath: String,
    navController: NavController
) {
    val viewModel: AddPostViewModel = hiltViewModel()
    val caption by viewModel.caption.collectAsStateWithLifecycle()
    val userId by viewModel.userId.collectAsStateWithLifecycle()
    val userInfo by viewModel.userInfo.collectAsStateWithLifecycle()
    var username: String = ""

    LaunchedEffect(key1 = userId, block = {
        viewModel.getUserInfo(userId!!)
    })
    LaunchedEffect(true) {
        viewModel.postState.collect { state ->
            when (state) {
                is Resource.Error -> {
                    val error = state.message
                    Log.d(TAG, "HA ? ${error.toString()}")
                }

                is Resource.Loading -> {
                    Log.d(TAG, "Loading")
                }

                is Resource.Success -> {
                    navController.navigate(Screens.FeedScreen.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = false
                        }
                    }
                }

                else -> {
                    Log.d(TAG, "Unexpected Error")
                }
            }
        }
    }

    if (userInfo != null) {
        username = userInfo!!.username
    }
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Row {
            Text(
                text = "New Post",
                color = Color.Black,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(0.75F),
                style = QuickSandTypography.titleLarge
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            Log.d(TAG, imagePath)
            val postImage = loadPicture(url = imagePath, defaultImage = DEFAULT_User_IMAGE).value
            postImage?.let {
                Image(bitmap = postImage.asImageBitmap(), contentDescription = "post Image")
            }
        }
        UsernameOrEmailTextField(
            label = "Write Caption",
            inputValue = caption,
            onQueryChanged = { viewModel.onCaptionChanged(it) },
            isError = false,
        )
        MyButton(text = "Share") {
            viewModel
                .createPost(
                    postDescription = caption,
                    userId = userId!!,
                    userName = username,
                    imageUrl =  imagePath.toUri()
                )
        }
    }
}

