package com.example.navapp

sealed class Screens(val route:String) {
    object MainScreen : Screens("mainScreen")
    object RegisterScreen : Screens("RegisterScreen")
    object FeedScreen : Screens("FeedScreen")
    object ForgotPasswordScreen : Screens("ForgotPasswordScreen")
    object ChangePasswordScreen : Screens("ChangePasswordScreen")
    object SettingsScreen : Screens("SettingsScreen")
    object SearchScreen : Screens("SearchScreen")

}