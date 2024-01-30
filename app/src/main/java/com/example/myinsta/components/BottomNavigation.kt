package com.example.myinsta.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
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
            route = Screens.MainScreen.route
        ),
        BottomNavigationItem(
            title = "Search",
            selectedItem = Icons.Filled.Search,
            unSelectedIcon = Icons.Outlined.Search,
            route = Screens.SearchScreen.route
        ),
        BottomNavigationItem(
            title = "Settings",
            selectedItem = Icons.Filled.Settings,
            unSelectedIcon = Icons.Outlined.Settings,
            route = Screens.SettingsScreen.route
        )
    )
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    NavigationBar {
        items.forEachIndexed { index, Item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(Item.route)
                },
                label = {
                    Text(text = Item.title)
                },
                icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex) Item.selectedItem else Item.unSelectedIcon,
                        contentDescription = Item.title
                    )
                }
            )
        }
    }
}