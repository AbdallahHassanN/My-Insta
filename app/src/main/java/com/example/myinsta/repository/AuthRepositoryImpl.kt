package com.example.myinsta.repository

import com.example.myinsta.common.await
import com.example.myinsta.response.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : AuthRepository {

    override suspend fun login(email: String, password: String): Flow<Resource<FirebaseUser>> {
        return try {
            val response = firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .await()
            flowOf(Resource.Success(response.user!!))
        } catch (e: Exception) {
            flowOf(Resource.Error("An unexpected error occurred"))
        }
    }

    override suspend fun register(
        email: String,
        password: String,
        username:String
    ):  Flow<Resource<FirebaseUser>>  {
        return try {
            val response = firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .await()
            flowOf(Resource.Success(response.user!!))
        } catch (e: Exception) {
            flowOf(Resource.Error("An unexpected error occurred"))
        }
    }

    override suspend fun logout(): Flow<Resource<Boolean>>
    = flow {
        try {
            emit(Resource.Loading(true))
            firebaseAuth.signOut()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: "UNKNOWN_ERROR_OCCURRED"))
        }
    }
}