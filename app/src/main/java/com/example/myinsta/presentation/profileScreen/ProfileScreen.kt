package com.example.myinsta.presentation.profileScreen

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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myinsta.R
import com.example.myinsta.components.CustomDivider
import com.example.myinsta.components.LogoImage
import com.example.myinsta.presentation.profileScreen.components.ProfileStates
import com.example.myinsta.presentation.profileScreen.components.RoundedImageView
import com.example.myinsta.presentation.registerScreen.RegisterScreen
import com.example.myinsta.ui.theme.QuickSandTypography
import com.example.navapp.Screens

@Composable
fun ProfileScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "UserName",
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
            RoundedImageView(painterResource(id = R.drawable.hakari))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                ProfileStates(
                    numberText = "5",
                    text = "Posts"
                ) {}
                ProfileStates(
                    numberText = "5",
                    text = "Followers"
                ) {}
                ProfileStates(
                    numberText = "5",
                    text = "Following"
                ) {}
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)
        ) {
            Text(
                text = "Account Name",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                style = QuickSandTypography.headlineMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Bio",
                color = Color.Black,
                style = QuickSandTypography.headlineSmall
            )
        }
        CustomDivider()
        LazyVerticalGrid(
            columns = GridCells.Fixed(3)
        ) {
            items(25) { index ->
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

@Preview(showBackground = true)
@Composable
fun DisplayProfileScreen() {
    val navController = rememberNavController()
    ProfileScreen(navController = navController)
}