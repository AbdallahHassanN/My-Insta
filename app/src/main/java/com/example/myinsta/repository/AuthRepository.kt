package com.example.myinsta.repository

import com.example.myinsta.models.User
import com.example.myinsta.response.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String)
            : Flow<Resource<FirebaseUser>>
    suspend fun register(email: String, password: String,
                         username:String,fullName:String)
    :  Flow<Resource<FirebaseUser?>>
    suspend fun logout()
            : Flow<Resource<Boolean>>
    fun verifyEmail()
    fun getUserId(): FirebaseUser?
    fun getUserInfo(userId: String): Flow<Resource<User?>>

}