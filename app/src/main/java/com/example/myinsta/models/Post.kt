package com.example.myinsta.models

import android.net.Uri
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date


data class Post(
    val postId :String = "",
    val postDescription : String="",
    val imageUrl: String = "",
    @ServerTimestamp
    val time : Timestamp = Timestamp(Date()),
    val userId : String = "",
    val userName : String = "",
    val notificationToken:String="",
    val likes:List<String> = emptyList()
)