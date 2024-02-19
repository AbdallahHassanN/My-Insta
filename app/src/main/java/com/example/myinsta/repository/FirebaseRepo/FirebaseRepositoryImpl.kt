package com.example.myinsta.repository.FirebaseRepo

import android.util.Log
import com.example.myinsta.common.Constants.COLLECTION_NAME
import com.example.myinsta.common.Constants.ERROR
import com.example.myinsta.common.Constants.TAG
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
    override fun getUsersByName(name: String): Flow<Resource<List<User>>> =
        callbackFlow {
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
                            // Exclude current user
                            .filter { user -> user.id != firebaseAuth.currentUser!!.uid }
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
                .collection(COLLECTION_NAME)
                .document(user.uid)
            //doc for the following
            val followingDocument = FirebaseFirestore
                .getInstance()
                .collection(COLLECTION_NAME)
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
                                followersList
                                    .add(user.uid)
                                Log.d(TAG,"HElp ${user.uid}")
                                userDocument
                                    .update("following", FieldValue.increment(1))
                                followingDocument
                                    .update("followers", FieldValue.increment(1))
                                followingDocument
                                    .update("followersList", followersList)
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

    override fun unfollowUser(id: String): Flow<Resource<Boolean>> = callbackFlow {
        firebaseAuth.currentUser?.let { user ->
            //doc for user
            val userDocument = FirebaseFirestore
                .getInstance()
                .collection(COLLECTION_NAME)
                .document(user.uid)
            //doc for the following
            val followingDocument = FirebaseFirestore
                .getInstance()
                .collection(COLLECTION_NAME)
                .document(id)

            userDocument
                .get()
                .addOnSuccessListener { snapshot ->
                    //2 docs to update the user and the following
                    val followingList =
                        snapshot["followingList"] as? MutableList<String> ?: mutableListOf()
                    val followersList =
                        snapshot["followersList"] as? MutableList<String> ?: mutableListOf()
                    if (followingList.contains(id)) {
                        followingList.remove(id)
                        userDocument
                            .update("followingList", followingList)
                            .addOnSuccessListener {
                                followersList.remove(user.uid)
                                userDocument
                                    .update("following", FieldValue.increment(-1))
                                followingDocument
                                    .update("followers", FieldValue.increment(-1))
                                followingDocument
                                    .update("followersList", followersList)
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

    override fun getFollowersListIds(id: String): Flow<Resource<List<String>>>
    = callbackFlow {
        try {
            fireStore
                .collection(COLLECTION_NAME)
                .document(id)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val followersList
                    = documentSnapshot.get("followersList") as? List<String>
                    trySend(Resource.Success(data = followersList!!))
                }
        } catch (e: Exception) {
            trySend(Resource.Error(message = e.localizedMessage ?: ERROR))
        }
        awaitClose()
    }

    override fun getFollowersInfo(ids: String): Flow<Resource<List<User>>>
            = callbackFlow {
            try {
                fireStore
                    .collection(COLLECTION_NAME)
                    .whereEqualTo("id", ids)
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        val users = querySnapshot
                            .documents
                            .mapNotNull { documentSnapshot ->
                                documentSnapshot.toObject(User::class.java)
                            }
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

    override fun getFollowingListIds(id: String): Flow<Resource<List<String>>>
    = callbackFlow {
        try {
            fireStore
                .collection(COLLECTION_NAME)
                .document(id)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val followersList
                            = documentSnapshot.get("followingList") as? List<String>
                    trySend(Resource.Success(data = followersList!!))
                }
        } catch (e: Exception) {
            trySend(Resource.Error(message = e.localizedMessage ?: ERROR))
        }
        awaitClose()
    }

    override fun getFollowingInfo(ids: String): Flow<Resource<List<User>>>
    = callbackFlow {
        try {
            fireStore
                .collection(COLLECTION_NAME)
                .whereEqualTo("id", ids)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val users = querySnapshot
                        .documents
                        .mapNotNull { documentSnapshot ->
                            documentSnapshot.toObject(User::class.java)
                        }
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
}