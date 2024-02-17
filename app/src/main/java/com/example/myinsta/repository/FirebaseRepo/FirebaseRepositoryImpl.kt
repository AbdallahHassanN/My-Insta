package com.example.myinsta.repository.FirebaseRepo

import com.example.myinsta.common.Constants.COLLECTION_NAME
import com.example.myinsta.common.Constants.ERROR
import com.example.myinsta.models.User
import com.example.myinsta.response.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


class FirebaseRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
) : FirebaseRepository {
    override fun getUsersByName(name: String): Flow<Resource<List<User>>> = callbackFlow {
        try {
            fireStore.collection(COLLECTION_NAME)
                .whereGreaterThanOrEqualTo("fullName", name)
                .whereLessThanOrEqualTo("fullName", "${name}\uF7FF")
                .addSnapshotListener { snapshot, e ->
                    if (snapshot != null) {
                        val user = snapshot.toObjects(User::class.java)
                        trySend(Resource.Success(data = user))
                    } else {
                        trySend(Resource.Error(e?.message ?: ERROR))
                    }
                }
        } catch (e: Exception) {
            trySend(Resource.Error(message = e.localizedMessage ?: ERROR))
        }
        awaitClose()
    }

}