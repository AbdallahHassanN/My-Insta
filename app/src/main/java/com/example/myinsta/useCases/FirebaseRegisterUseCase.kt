package com.example.myinsta.useCases

import android.content.Context
import android.util.Log
import com.example.myinsta.common.Constants
import com.example.myinsta.repository.authRepo.AuthRepository
import com.example.myinsta.response.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FirebaseRegisterUseCase @Inject constructor(
    private val repo: AuthRepository,
    @ApplicationContext private val context: Context
) {
    suspend fun execute(
        email: String, password: String,username:String,fullName:String
    ) = repo
        .register(email,password,username,fullName).flatMapConcat {
        when (it) {
            is Resource.Error -> {
                Log.d(Constants.TAG, "UseCase Error ? ${it.message.toString()}")
                flowOf(Resource.Error(it.message.toString()))
            }

            is Resource.Loading -> {
                Log.d(Constants.TAG, "UseCase Loading ?")
                flowOf(Resource.Loading())
            }

            is Resource.Success -> {
                Log.d(Constants.TAG, "UseCase Success ? ${it.data!!.email}")
                flowOf(Resource.Success(it.data))
            }
        }
    }
}