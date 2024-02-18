package com.example.myinsta.useCases

import android.content.Context
import com.example.myinsta.repository.FirebaseRepo.FirebaseRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FirebaseFollowUser  @Inject constructor(
    private val repo: FirebaseRepository,
) {
    fun execute(id:String) = repo.followUser(id)
}