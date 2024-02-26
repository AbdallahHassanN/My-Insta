package com.example.myinsta.useCases

import com.example.myinsta.repository.firebaseRepo.FirebaseRepository
import javax.inject.Inject

class FirebaseFollowUser  @Inject constructor(
    private val repo: FirebaseRepository,
) {
    fun execute(id:String) = repo.followUser(id)
}