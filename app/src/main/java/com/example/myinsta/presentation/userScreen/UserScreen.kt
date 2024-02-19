package com.example.myinsta.presentation.userScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
fun UserScreen(
    id: String,
    navController: NavController
) {
    val viewModel: UserScreenViewModel = hiltViewModel()
    val userInfo by viewModel.userInfo.collectAsStateWithLifecycle()
    val currentUserId by viewModel.currentUserId.collectAsStateWithLifecycle()

    var btnState:Boolean = false
    var username: String = ""
    var fullName: String = ""
    var bio: String = ""
    var img: String = ""
    var followers: Int = 0
    var following: Int = 0
    var posts: Int = 0

    LaunchedEffect(key1 = id, block = {
        viewModel.getUserInfo(id)
    })
    if (userInfo != null) {
        username = userInfo!!.username
        fullName = userInfo!!.fullName
        bio = userInfo!!.bio
        followers = userInfo!!.followers
        following = userInfo!!.following
        posts = userInfo!!.totalPosts
        img = userInfo!!.imageUrl
        btnState = if(userInfo!!.followersList.contains(currentUserId)){
            viewModel.changeToFollowState()
            true
        }else{
            viewModel.changeToUnfollowState()
            false
        }
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
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            RoundedImageView(img)
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
                    navController.navigate(Screens.FollowersListScreen.withArgs(id))
                }
                ProfileStates(
                    numberText = following.toString(),
                    text = "Following"
                ) {
                    navController.navigate(Screens.FollowingListScreen.withArgs(id))
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
                Button(
                    modifier = Modifier.padding(end = 5.dp),
                    colors = ButtonDefaults
                        .buttonColors(
                        containerColor =
                        if (btnState) Color.Green else Color.Blue
                    ),
                    onClick = {
                        btnState = if(!(userInfo!!.followersList.contains(currentUserId))){
                            viewModel.followUser(id)
                            viewModel.changeToFollowState()
                            true
                        }else{
                            viewModel.unfollowUser(id)
                            viewModel.changeToUnfollowState()
                            false
                        }
                    }
                ) {
                    Text(
                        text = if (btnState) "Following" else "Follow",
                        fontSize = 16.sp
                    )
                }
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