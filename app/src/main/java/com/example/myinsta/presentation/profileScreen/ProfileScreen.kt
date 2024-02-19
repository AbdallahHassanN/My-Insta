package com.example.myinsta.presentation.profileScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.myinsta.R
import com.example.myinsta.common.Constants.TAG
import com.example.myinsta.components.CustomDivider
import com.example.myinsta.presentation.profileScreen.components.ProfileStates
import com.example.myinsta.presentation.profileScreen.components.RoundedImageView
import com.example.myinsta.ui.theme.QuickSandTypography
import com.example.navapp.Screens

@Composable
fun ProfileScreen(
    //id: String,
    navController: NavController
) {
    val viewModel: ProfileScreenViewModel = hiltViewModel()
    val userInfo by viewModel.userInfo.collectAsStateWithLifecycle()
    val userId by viewModel.userId.collectAsStateWithLifecycle()
    var username: String = ""
    var fullName: String = ""
    var bio: String = ""
    var img: String = ""
    var followers: Int = 0
    var following: Int = 0
    var posts: Int = 0

    LaunchedEffect(key1 = userId, block = {
        viewModel.getUserInfo(userId!!)
    })

    if (userInfo != null) {
        username = userInfo!!.username
        fullName = userInfo!!.fullName
        bio = userInfo!!.bio
        followers = userInfo!!.followers
        following = userInfo!!.following
        posts = userInfo!!.totalPosts
        img = userInfo!!.imageUrl
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = username,
                color = Color.Black,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(0.8F),
                style = QuickSandTypography.headlineMedium
            )
            Icon(
                imageVector = Icons.Rounded.AddCircle,
                contentDescription = "",
                modifier = Modifier
                    .padding(top = 15.dp)
                    .clickable(enabled = true,
                        onClick = {
                            navController.navigate(Screens.AddPostScreen.route)
                        })
            )

            Icon(
                imageVector = Icons.Rounded.Settings,
                contentDescription = "",
                modifier = Modifier
                    .padding(top = 15.dp, start = 15.dp)
                    .clickable(enabled = true,
                        onClick = {
                            navController.navigate(Screens.SettingsScreen.route)
                        })
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            RoundedImageView(img)
            Log.d(TAG, "HH$img")
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                ProfileStates(
                    numberText = posts.toString(),
                    text = "Posts"
                ) {}
                ProfileStates(
                    numberText = followers.toString(),
                    text = "Followers"
                ) {
                    navController.navigate(Screens.FollowersListScreen.withArgs(userId!!))

                }
                ProfileStates(
                    numberText = following.toString(),
                    text = "Following"
                ) {
                    navController.navigate(Screens.FollowingListScreen.withArgs(userId!!))
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)
        ) {
            Text(
                text = fullName,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                style = QuickSandTypography.headlineMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(
                    text = bio,
                    color = Color.Black,
                    style = QuickSandTypography.headlineSmall,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        CustomDivider()
        LazyVerticalGrid(
            columns = GridCells.Fixed(3)
        ) {
            items(25) {
                Image(
                    painter = painterResource(id = R.drawable.hakari),
                    contentDescription = null,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .border(1.dp, Color.Black),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}