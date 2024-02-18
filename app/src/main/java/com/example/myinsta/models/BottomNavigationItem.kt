package com.example.myinsta.models

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val title:String,
    val selectedItem:ImageVector,
    val unSelectedIcon:ImageVector,
    val route:String,
    val id:Int
)
