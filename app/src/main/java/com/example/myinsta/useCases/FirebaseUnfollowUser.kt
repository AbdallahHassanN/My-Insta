package com.example.myinsta.useCases

import com.example.myinsta.repository.firebaseRepo.FirebaseRepository
import javax.inject.Inject

class FirebaseUnfollowUser  @Inject constructor(
    private val repo: FirebaseRepository,
) {
    fun execute(id:String) = repo.unfollowUser(id)
}