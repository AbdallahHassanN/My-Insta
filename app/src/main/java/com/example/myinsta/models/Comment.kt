package com.example.myinsta.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Comment(
    val commentId:String="",
    val comment:String="",
    val userId:String="",
    val authorName:String="",
    @ServerTimestamp
    val time : Timestamp = Timestamp(Date())
)
