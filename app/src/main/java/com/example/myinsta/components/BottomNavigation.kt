package com.example.myinsta.components

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.myinsta.common.Constants.TAG
import com.example.myinsta.models.BottomNavigationItem
import com.example.navapp.Screens

@Composable
fun BottomNavigation(
    navController: NavController
) {
    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedItem = Icons.Filled.Home,
            unSelectedIcon = Icons.Outlined.Home,
            route = Screens.FeedScreen.route
        ),
        BottomNavigationItem(
            title = "Search",
            selectedItem = Icons.Filled.Search,
            unSelectedIcon = Icons.Outlined.Search,
            route = Screens.SearchScreen.route
        ),
        BottomNavigationItem(
            title = "Add Post",
            selectedItem = Icons.Filled.AddCircle,
            unSelectedIcon = Icons.Outlined.AddCircle,
            route = Screens.AddPostScreen.route
        ),
        BottomNavigationItem(
            title = "Profile",
            selectedItem = Icons.Filled.AccountCircle,
            unSelectedIcon = Icons.Outlined.AccountCircle,
            route = Screens.ProfileScreen.route
        ),
    )
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.route)
                    Log.d(TAG,item.route)
                },
                label = {
                    Text(text = item.title)
                },
                icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex) item.selectedItem else item.unSelectedIcon,
                        contentDescription = item.title
                    )
                }
            )
        }
    }
}