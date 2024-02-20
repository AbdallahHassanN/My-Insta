package com.example.myinsta.models

data class User(
    val id: String = "",
    val fullName: String = "",
    val username:String ="",
    val email: String = "",
    val password: String = "",
    val imageUrl: String = "",
    val following: Int=0,
    val followers: Int=0,
    val chats:List<String> = emptyList(),
    val followersList : List<String> = emptyList(),
    val followingList : List<String> = emptyList(),
    val totalPosts: Int = 0,
    val postsId: List<String> = emptyList(),
    val bio: String = "",
)

