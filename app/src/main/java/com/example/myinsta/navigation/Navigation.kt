package com.example.myinsta.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myinsta.common.Constants.USER_ID
import com.example.myinsta.presentation.chatScreen.ChatScreen
import com.example.myinsta.presentation.settingsScreen.changeNameScreen.ChangeNameScreen
import com.example.myinsta.presentation.settingsScreen.changeNameScreen.ChangeUsernameScreen
import com.example.myinsta.presentation.settingsScreen.SettingsScreen
import com.example.myinsta.presentation.addPostScreen.AddPostScreen
import com.example.myinsta.presentation.feedScreen.FeedScreen
import com.example.myinsta.presentation.mainScreen.MainScreen
import com.example.myinsta.presentation.notificationScreen.NotificationScreen
import com.example.myinsta.presentation.profileScreen.ProfileScreen
import com.example.myinsta.presentation.profileScreen.ProfileScreenViewModel
import com.example.myinsta.presentation.registerScreen.RegisterScreen
import com.example.myinsta.presentation.searchScreen.SearchScreen
import com.example.myinsta.presentation.settingsScreen.changeBioScreen.ChangeBioScreen
import com.example.navapp.Screens

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.MainScreen.route) {
        composable(route = Screens.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(route = Screens.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }
        composable(route = Screens.FeedScreen.route) {
            FeedScreen(navController = navController)
        }
        composable(route = Screens.SearchScreen.route) {
            SearchScreen(navController = navController)
        }
        /*composable(route = Screens.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }*/
        /*composable(route = Screens.ProfileScreen.route + "/{${USER_ID}}",
            arguments = listOf(navArgument(USER_ID){
                type = NavType.StringType
            })
        ){
            it.arguments!!
                .getString(USER_ID)
                ?.let { it1 -> ProfileScreen(id = it1,navController = navController) }
        }*/
        composable(
            route = Screens.ProfileScreen.route + "/{$USER_ID}",
            arguments = listOf(navArgument(USER_ID) { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString(USER_ID) ?: ""
            ProfileScreen(id = userId, navController = navController)
        }
        composable(route = Screens.AddPostScreen.route) {
            AddPostScreen(navController = navController)
        }
        composable(route = Screens.SettingsScreen.route) {
            SettingsScreen(navController = navController)
        }
        composable(route = Screens.ChangeNameScreen.route) {
            ChangeNameScreen(navController = navController)
        }
        composable(route = Screens.ChangeUsernameScreen.route) {
            ChangeUsernameScreen(navController = navController)
        }
        composable(route = Screens.ChangeBioScreen.route) {
            ChangeBioScreen(navController = navController)
        }
        composable(route = Screens.NotificationScreen.route) {
            NotificationScreen(navController = navController)
        }
        composable(route = Screens.ChatScreen.route) {
            ChatScreen()
        }
    }
}