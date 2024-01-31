package com.example.navapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myinsta.presentation.SettingsScreen.SettingsScreen
import com.example.myinsta.presentation.addPostScreen.AddPostScreen
import com.example.myinsta.presentation.changePasswordScreen.ChangePasswordScreen
import com.example.myinsta.presentation.feedScreen.FeedScreen
import com.example.myinsta.presentation.forgotPasswordScreen.ForgotPasswordScreen
import com.example.myinsta.presentation.mainScreen.MainScreen
import com.example.myinsta.presentation.profileScreen.ProfileScreen
import com.example.myinsta.presentation.profileScreen.components.ProfileStates
import com.example.myinsta.presentation.registerScreen.RegisterScreen
import com.example.myinsta.presentation.searchScreen.SearchScreen

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
        composable(route = Screens.ForgotPasswordScreen.route) {
            ForgotPasswordScreen(navController = navController)
        }
        composable(route = Screens.ChangePasswordScreen.route) {
            ChangePasswordScreen(navController = navController)
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
        composable(route = Screens.AddPostScreen.route) {
            /*TODO*/
            AddPostScreen(navController = navController)
        }
        composable(route = Screens.SettingsScreen.route) {
            /*TODO*/
            SettingsScreen(navController = navController)
        }

    }
}