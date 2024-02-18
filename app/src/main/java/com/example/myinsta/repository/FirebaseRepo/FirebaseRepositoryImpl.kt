package com.example.myinsta.repository.FirebaseRepo

import com.example.myinsta.common.Constants.COLLECTION_NAME
import com.example.myinsta.common.Constants.ERROR
import com.example.myinsta.models.User
import com.example.myinsta.response.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


class FirebaseRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
) : FirebaseRepository {
    override fun getUsersByName(name: String): Flow<Resource<List<User>>> = callbackFlow {
        try {
            fireStore.collection(COLLECTION_NAME)
                .whereGreaterThanOrEqualTo("fullName", name)
                .whereLessThanOrEqualTo("fullName", "${name}\uF7FF")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val users = querySnapshot
                        .documents
                        .mapNotNull { documentSnapshot ->
                            documentSnapshot.toObject(User::class.java)
                        }
                        .filter { user -> user.id != firebaseAuth.currentUser!!.uid } // Exclude current user
                    trySend(Resource.Success(data = users))
                }
                .addOnFailureListener { e ->
                    trySend(Resource.Error(e.message ?: ERROR))
                }
        } catch (e: Exception) {
            trySend(Resource.Error(message = e.localizedMessage ?: ERROR))
        }
        awaitClose()
    }

    override fun followUser(id: String): Flow<Resource<Boolean>> = callbackFlow {
        firebaseAuth.currentUser?.let { user ->
            //doc for user
            val userDocument = FirebaseFirestore
                .getInstance()
                .collection("users")
                .document(user.uid)
            //doc for the following
            val followingDocument = FirebaseFirestore
                .getInstance()
                .collection("users")
                .document(id)

            userDocument
                .get()
                .addOnSuccessListener { snapshot ->
                    //2 docs to update the user and the following
                    val followingList =
                        snapshot["followingList"] as? MutableList<String> ?: mutableListOf()
                    val followersList =
                        snapshot["followersList"] as? MutableList<String> ?: mutableListOf()
                    if (!followingList.contains(id)) {
                        followingList.add(id)
                        userDocument
                            .update("followingList", followingList)
                            .addOnSuccessListener {
                                followersList.add(user.uid)
                                userDocument
                                    .update("following",FieldValue.increment(1))
                                followingDocument
                                    .update("followers",FieldValue.increment(1))
                                followingDocument
                                    .update("followersList",followersList)
                                trySend(Resource.Success(true))
                            }.addOnFailureListener {
                                trySend(Resource.Error(it.message!!))
                            }
                    } else {
                        trySend(Resource.Success(true))
                    }
                }.addOnFailureListener {
                    trySend(Resource.Error(it.message!!))
                }
        }
        awaitClose()
    }

}