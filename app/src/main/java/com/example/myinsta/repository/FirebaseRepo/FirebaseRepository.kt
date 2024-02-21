package com.example.myinsta.repository.FirebaseRepo

import android.net.Uri
import com.example.myinsta.models.Post
import com.example.myinsta.models.User
import com.example.myinsta.response.Resource
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {
    fun getUsersByName(name: String)
            : Flow<Resource<List<User>>>

    fun followUser(id: String)
            : Flow<Resource<Boolean>>

    fun unfollowUser(id: String)
            : Flow<Resource<Boolean>>

    fun getFollowersListIds(id: String)
            : Flow<Resource<List<String>>>

    fun getFollowersInfo(ids: String)
            : Flow<Resource<List<User>>>

    fun getFollowingListIds(id: String)
            : Flow<Resource<List<String>>>

    fun getFollowingInfo(ids: String)
            : Flow<Resource<List<User>>>

    fun createPost(
        postId: String,
        postDescription: String,
        userId: String,
        userName: String,
        imageUrl: String
    ): Flow<Resource<Boolean>>

    fun getAllPostsListIds(id: String)
            : Flow<Resource<List<String>>>

    fun getAllPostsInfo(ids: String)
            : Flow<Resource<Post>>

    fun getUsersPosts(idList: List<String>)
            : Flow<Resource<List<Post>>>
}