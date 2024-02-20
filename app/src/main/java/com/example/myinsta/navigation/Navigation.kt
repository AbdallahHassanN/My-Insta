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
import com.example.myinsta.common.Constants.IMAGE_PATH
import com.example.myinsta.common.Constants.USER_ID
import com.example.myinsta.presentation.chatScreen.ChatScreen
import com.example.myinsta.presentation.settingsScreen.changeNameScreen.ChangeNameScreen
import com.example.myinsta.presentation.settingsScreen.changeNameScreen.ChangeUsernameScreen
import com.example.myinsta.presentation.settingsScreen.SettingsScreen
import com.example.myinsta.presentation.addPostScreen.AddPostScreen
import com.example.myinsta.presentation.addPostScreen.confirmPost.ConfirmPostScreen
import com.example.myinsta.presentation.feedScreen.FeedScreen
import com.example.myinsta.presentation.mainScreen.MainScreen
import com.example.myinsta.presentation.notificationScreen.NotificationScreen
import com.example.myinsta.presentation.profileScreen.ProfileScreen
import com.example.myinsta.presentation.profileScreen.ProfileScreenViewModel
import com.example.myinsta.presentation.registerScreen.RegisterScreen
import com.example.myinsta.presentation.searchScreen.SearchScreen
import com.example.myinsta.presentation.settingsScreen.changeBioScreen.ChangeBioScreen
import com.example.myinsta.presentation.userScreen.UserScreen
import com.example.myinsta.presentation.userScreen.followersListScreen.FollowersListScreen
import com.example.myinsta.presentation.userScreen.followersListScreen.FollowingListScreen
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
        composable(route = Screens.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }
        composable(
            route = Screens.UserScreen.route + "/{$USER_ID}",
            arguments = listOf(navArgument(USER_ID) { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString(USER_ID) ?: ""
            UserScreen(id = userId, navController = navController)
        }
        composable(
            route = Screens.FollowersListScreen.route + "/{$USER_ID}",
            arguments = listOf(navArgument(USER_ID) { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString(USER_ID) ?: ""
            FollowersListScreen(id = userId, navController = navController)
        }
        composable(
            route = Screens.FollowingListScreen.route + "/{$USER_ID}",
            arguments = listOf(navArgument(USER_ID) { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString(USER_ID) ?: ""
            FollowingListScreen(id = userId, navController = navController)
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
        composable(
            route = Screens.ConfirmPost.route + "/{$IMAGE_PATH}",
            arguments = listOf(navArgument(IMAGE_PATH) { type = NavType.StringType })
        ) { backStackEntry ->
            val imagePath = backStackEntry.arguments?.getString(IMAGE_PATH) ?: ""
            ConfirmPostScreen(imagePath = imagePath, navController = navController)
        }
    }
}