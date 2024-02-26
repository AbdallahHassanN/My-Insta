package com.example.myinsta.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class ChatUserList(
    val id:String="",
    val friendUsername:String="",
    val lastMessage:String="",
    val imgUrl:String="",
    @ServerTimestamp
    var lastMessageTime: Timestamp = Timestamp(Date())
)
