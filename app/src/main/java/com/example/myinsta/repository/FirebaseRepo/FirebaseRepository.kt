package com.example.myinsta.repository.FirebaseRepo

import com.example.myinsta.models.User
import com.example.myinsta.response.Resource
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {
    fun getUsersByName(name: String)
            : Flow<Resource<List<User>>>

}