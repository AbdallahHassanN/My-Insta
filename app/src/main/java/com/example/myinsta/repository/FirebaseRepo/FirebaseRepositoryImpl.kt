package com.example.myinsta.repository.FirebaseRepo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.example.myinsta.common.Constants.COLLECTION_USERS
import com.example.myinsta.common.Constants.COLLECTION_POSTS
import com.example.myinsta.common.Constants.ERROR
import com.example.myinsta.common.Constants.TAG
import com.example.myinsta.common.await
import com.example.myinsta.common.compressImage
import com.example.myinsta.common.uploadCompressedImage
import com.example.myinsta.models.Post
import com.example.myinsta.models.User
import com.example.myinsta.response.Resource
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val appContext: Context
) : FirebaseRepository {
    override fun getUsersByName(name: String): Flow<Resource<List<User>>> =
        callbackFlow {
            try {
                fireStore.collection(COLLECTION_USERS)
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
                .collection(COLLECTION_USERS)
                .document(user.uid)
            //doc for the following
            val followingDocument = FirebaseFirestore
                .getInstance()
                .collection(COLLECTION_USERS)
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
                                Log.d(TAG, "HElp ${user.uid}")
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
                .collection(COLLECTION_USERS)
                .document(user.uid)
            //doc for the following
            val followingDocument = FirebaseFirestore
                .getInstance()
                .collection(COLLECTION_USERS)
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

    override fun getFollowersListIds(id: String): Flow<Resource<List<String>>> = callbackFlow {
        try {
            fireStore
                .collection(COLLECTION_USERS)
                .document(id)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val followersList = documentSnapshot.get("followersList") as? List<String>
                    trySend(Resource.Success(data = followersList!!))
                }
        } catch (e: Exception) {
            trySend(Resource.Error(message = e.localizedMessage ?: ERROR))
        }
        awaitClose()
    }

    override fun getFollowersInfo(ids: String): Flow<Resource<List<User>>> = callbackFlow {
        try {
            fireStore
                .collection(COLLECTION_USERS)
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

    override fun getFollowingListIds(id: String): Flow<Resource<List<String>>> = callbackFlow {
        try {
            fireStore
                .collection(COLLECTION_USERS)
                .document(id)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val followersList = documentSnapshot.get("followingList") as? List<String>
                    trySend(Resource.Success(data = followersList!!))
                }
        } catch (e: Exception) {
            trySend(Resource.Error(message = e.localizedMessage ?: ERROR))
        }
        awaitClose()
    }

    override fun getFollowingInfo(ids: String): Flow<Resource<List<User>>> = callbackFlow {
        try {
            fireStore
                .collection(COLLECTION_USERS)
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

    override fun createPost(
        postId: String,
        postDescription: String,
        userId: String,
        userName: String,
        imageUrl: Uri
    ): Flow<Resource<Boolean>> = callbackFlow {
        trySend(Resource.Loading(true))
        try {
            val postRef = fireStore
                .collection(COLLECTION_POSTS)

            val userRef = fireStore
                .collection(COLLECTION_USERS)
                .document(userId)

            val post = Post(
                postId = postId,
                postDescription = postDescription,
                time = Timestamp.now(),
                userId = userId,
                userName = userName,
                imageUrl = imageUrl
            )

            // Add the post to Firestore
            postRef
                .document(postId)
                .set(post)
                .addOnSuccessListener {
                    // Update user's postsId and totalPosts
                    val userDocument =
                        fireStore
                            .collection(COLLECTION_USERS)
                            .document(userId)
                    userDocument
                        .get()
                        .addOnSuccessListener { snapshot ->
                            val postsIdList =
                                (snapshot["postsId"] as? MutableList<String>) ?: mutableListOf()
                            if (!postsIdList.contains(postId)) {
                                postsIdList.add(postId)
                                userRef
                                    .update(
                                        "postsId",
                                        postsIdList,
                                        "totalPosts",
                                        FieldValue.increment(1)
                                    )
                            }
                            // Launch a coroutine to upload the image
                            launch {
                                try {
                                    uploadImagePost(imageUrl, postId)
                                    trySend(Resource.Success(true))
                                } catch (e: Exception) {
                                    trySend(Resource.Error(e.message ?: ERROR))
                                }
                            }
                        }
                }
                .addOnFailureListener { e ->
                    trySend(Resource.Error(e.message ?: ERROR))
                }
        } catch (e: Exception) {
            trySend(Resource.Error(e.message ?: ERROR))
        } finally {
            trySend(Resource.Loading(isLoading = false))
            awaitClose()
        }
    }

    private suspend fun uploadImagePost(uri: Uri, path: String) {
        try {
            val compressedImageUri = compressImage(uri,appContext)
            val success = uploadCompressedImage(
                compressedImageUri,
                path,
                storage
            )
            Log.d(TAG, "is 1 ? $success")
            Log.d(TAG, "is 2 ? $compressedImageUri")
            Log.d(TAG, "is 3 ? $path")

            if (success) {
                // Get download URL
                val downloadUrl = storage.getReference(path).downloadUrl.await()

                // Update post document with the download URL
                val postDocument = fireStore.collection(COLLECTION_POSTS).document(path)
                postDocument.update("imageUrl", downloadUrl.toString()).await()
                Log.d(TAG, "Image Uploaded")
            } else {
                Log.d(TAG, "Failed")
            }
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
        }
    }
}