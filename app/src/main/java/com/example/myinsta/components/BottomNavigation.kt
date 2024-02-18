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
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myinsta.common.Constants.TAG
import com.example.myinsta.common.Constants.USER_ID
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
            route = Screens.FeedScreen.route,
            id = 0
        ),
        BottomNavigationItem(
            title = "Search",
            selectedItem = Icons.Filled.Search,
            unSelectedIcon = Icons.Outlined.Search,
            route = Screens.SearchScreen.route,
            id = 1
        ),
        BottomNavigationItem(
            title = "Add Post",
            selectedItem = Icons.Filled.AddCircle,
            unSelectedIcon = Icons.Outlined.AddCircle,
            route = Screens.AddPostScreen.route,
            id = 2
        ),
        BottomNavigationItem(
            title = "Profile",
            selectedItem = Icons.Filled.AccountCircle,
            unSelectedIcon = Icons.Outlined.AccountCircle,
            route = "${Screens.ProfileScreen.route}/$USER_ID",
            id = 3
        ),
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                label = {
                    Text(text = item.title)
                },
                icon = {
                    Icon(
                        imageVector = if (index == item.id) item.selectedItem
                        else item.unSelectedIcon,
                        contentDescription = item.title
                    )
                }
            )
        }
    }
}