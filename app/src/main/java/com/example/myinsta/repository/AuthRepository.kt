package com.example.myinsta.repository

import com.example.myinsta.response.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String)
            : Flow<Resource<FirebaseUser>>

    suspend fun register(name: String, email: String, password: String): Resource<FirebaseUser>
    suspend fun logout()
            : Flow<Resource<Boolean>>

}