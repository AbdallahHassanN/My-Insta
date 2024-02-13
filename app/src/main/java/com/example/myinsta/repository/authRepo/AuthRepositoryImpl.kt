package com.example.myinsta.repository.authRepo

import com.example.myinsta.common.Constants.COLLECTION_NAME
import com.example.myinsta.common.Constants.ERROR
import com.example.myinsta.common.await
import com.example.myinsta.models.User
import com.example.myinsta.response.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth, private val fireStore: FirebaseFirestore
) : AuthRepository {

    override suspend fun login(email: String, password: String): Flow<Resource<FirebaseUser>> {
        return try {
            val response = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            flowOf(Resource.Success(response.user!!))
        } catch (e: Exception) {
            flowOf(Resource.Error(ERROR))
        }
    }

    override suspend fun register(
        email: String, password: String, username: String, fullName: String
    ): Flow<Resource<FirebaseUser?>> = callbackFlow {
        try {
            trySend(Resource.Loading(true))
            val usernameExists =
                fireStore.collection(COLLECTION_NAME).whereEqualTo("username", username).get()
                    .await().size() > 0
            if (usernameExists) {
                // Username already exists, return error
                trySend(Resource.Error(message = "Username already exists. Please choose a different one."))

                close()
                return@callbackFlow
            }
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                firebaseAuth.currentUser?.let { user ->
                    val profileUpdates = userProfileChangeRequest {
                        displayName = username
                    }
                    user.updateProfile(profileUpdates).addOnSuccessListener {
                        val userId = firebaseAuth.currentUser?.uid!!
                        val userInfo = User(
                            id = userId,
                            username = username,
                            email = email,
                            password = password,
                            fullName = fullName
                        )
                        fireStore.collection(COLLECTION_NAME).document(userId).set(userInfo)
                            .addOnSuccessListener {
                                verifyEmail()
                                trySend(Resource.Success(firebaseAuth.currentUser))
                            }.addOnFailureListener {
                                trySend(
                                    Resource.Error(
                                        message = it.localizedMessage ?: ERROR
                                    )
                                )
                            }
                    }.addOnFailureListener {
                        trySend(
                            Resource.Error(
                                message = it.localizedMessage ?: ERROR
                            )
                        )
                    }
                }
            }.addOnFailureListener {
                trySend(
                    Resource.Error(
                        message = it.localizedMessage ?: ERROR
                    )
                )
            }
        } catch (e: Exception) {
            trySend(Resource.Error(message = e.localizedMessage ?: ERROR))
        }
        awaitClose()
    }

    override suspend fun logout(): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading(true))
            firebaseAuth.signOut()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: ERROR))
        }
    }

    override fun verifyEmail() {
        firebaseAuth.currentUser?.let {
            it.sendEmailVerification().addOnFailureListener {
                verifyEmail()
            }
        }
    }

    override fun getUserId(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override fun getUserInfo(userId: String): Flow<Resource<User?>> = callbackFlow {
        val snapshotListener = fireStore.collection(COLLECTION_NAME).document(userId)
            .addSnapshotListener { snapshot, e ->
                if (snapshot != null) {
                    val user = snapshot.toObject(User::class.java)
                    trySend(Resource.Success(user))
                } else {
                    trySend(Resource.Error(e?.message ?: ERROR))
                }
            }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override fun changeName(newName: String): Flow<Resource<Boolean>> =
        callbackFlow {
            firebaseAuth.currentUser?.let { user ->
                val userDocument =
                    FirebaseFirestore
                        .getInstance()
                        .collection("users")
                        .document(user.uid)

                userDocument.update("fullName", newName)
                    .addOnSuccessListener {
                        trySend(Resource.Success(true))
                    }.addOnFailureListener {
                        trySend(Resource.Error(it.message ?: ERROR))
                    }
            } ?: run {
                trySend(Resource.Error("User not logged in"))
            }
            awaitClose()
        }

    override fun changeBio(newBio: String): Flow<Resource<Boolean>>  =
        callbackFlow {
            firebaseAuth.currentUser?.let { user ->
                val userDocument =
                    FirebaseFirestore
                        .getInstance()
                        .collection("users")
                        .document(user.uid)

                userDocument.update("bio", newBio)
                    .addOnSuccessListener {
                        trySend(Resource.Success(true))
                    }.addOnFailureListener {
                        trySend(Resource.Error(it.message ?: ERROR))
                    }
            } ?: run {
                trySend(Resource.Error("User not logged in"))
            }
            awaitClose()
        }

    override fun changeUsername(newUsername: String): Flow<Resource<Boolean>> =
        callbackFlow {
            trySend(Resource.Loading(true))
            val usernameExists =
                fireStore.collection(COLLECTION_NAME).whereEqualTo("username", newUsername).get()
                    .await().size() > 0
            if (usernameExists) {
                trySend(Resource.Error(message = "Username already exists. Please choose a different one."))
                close()
                return@callbackFlow
            }
            firebaseAuth.currentUser?.let { user ->
                val userDocument =
                    FirebaseFirestore
                        .getInstance()
                        .collection("users")
                        .document(user.uid)

                userDocument.update("username", newUsername)
                    .addOnSuccessListener {
                        trySend(Resource.Success(true))
                    }.addOnFailureListener {
                        trySend(Resource.Error(it.message ?: ERROR))
                    }
            } ?: run {
                trySend(Resource.Error("User not logged in"))
            }
            awaitClose()
        }

}
