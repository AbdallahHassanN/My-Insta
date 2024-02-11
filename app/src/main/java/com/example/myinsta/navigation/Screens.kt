package com.example.navapp

sealed class Screens(val route:String) {
    object MainScreen : Screens("mainScreen")
    object RegisterScreen : Screens("RegisterScreen")
    object ForgotPasswordScreen : Screens("ForgotPasswordScreen")
    object FeedScreen : Screens("FeedScreen")
    object ProfileScreen : Screens("ProfileScreen")
    object SearchScreen : Screens("SearchScreen")
    object AddPostScreen : Screens("AddPostScreen")
    object SettingsScreen : Screens("SettingsScreen")
    object ChangeNameScreen : Screens("ChangeNameScreen")
    object ChangeUsernameScreen : Screens("ChangeUsernameScreen")
    object NotificationScreen:Screens("NotificationScreen")
    object ChatScreen:Screens("ChatScreen")

}