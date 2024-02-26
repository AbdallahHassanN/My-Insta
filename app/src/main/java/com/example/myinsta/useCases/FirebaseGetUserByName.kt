package com.example.myinsta.useCases

import android.content.Context
import android.util.Log
import com.example.myinsta.common.Constants
import com.example.myinsta.repository.firebaseRepo.FirebaseRepository
import com.example.myinsta.response.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FirebaseGetUserByName @Inject constructor(
    private val repo: FirebaseRepository,
    @ApplicationContext private val context: Context
) {
    fun execute(
        name: String
    ) = repo.getUsersByName(name).flatMapConcat {
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
                Log.d(Constants.TAG, "UseCase Success ? ${it.data!!}")
                flowOf(Resource.Success(it.data))
            }
        }
    }
}