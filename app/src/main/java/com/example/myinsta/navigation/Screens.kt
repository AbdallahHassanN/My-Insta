package com.example.navapp

sealed class Screens(val route:String) {
    object MainScreen : Screens("mainScreen")
    object RegisterScreen : Screens("RegisterScreen")
    object FeedScreen : Screens("FeedScreen")
    object ProfileScreen : Screens("ProfileScreen")
    object SearchScreen : Screens("SearchScreen")
    object AddPostScreen : Screens("AddPostScreen")
    object SettingsScreen : Screens("SettingsScreen")
    object ChangeNameScreen : Screens("ChangeNameScreen")
    object ChangeUsernameScreen : Screens("ChangeUsernameScreen")
    object ChangeBioScreen : Screens("ChangeBioScreen")

    object NotificationScreen:Screens("NotificationScreen")
    object ChatScreen:Screens("ChatScreen")

    object UserScreen:Screens("UserScreen")

    fun withArgs(vararg args:String):String {
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }

}