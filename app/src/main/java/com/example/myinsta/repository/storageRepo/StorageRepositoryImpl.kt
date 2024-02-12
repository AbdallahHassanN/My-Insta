package com.example.myinsta.repository.storageRepo

import android.net.Uri
import com.example.myinsta.common.Constants.ERROR
import com.example.myinsta.response.Resource
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class StorageRepositoryImpl
@Inject constructor(
    private val storage: FirebaseStorage
) : StorageRepository {
    override suspend fun uploadImg(uri: Uri, path: String)
            : Flow<Resource<Boolean>> = callbackFlow {

        trySend(Resource.Loading(true))
        try {
            storage.getReference(path).putFile(uri)
                .addOnSuccessListener {
                    trySend(Resource.Success(true))
                }.addOnFailureListener {

                    trySend(Resource.Error(it.message ?: ERROR))
                }
        } catch (e: Exception) {
            trySend(Resource.Error(e.message ?: ERROR))
        }
        trySend(Resource.Loading(false))
        awaitClose()
    }

    override suspend fun getImg(path: String)
            : Flow<Resource<Uri>> = callbackFlow {
        trySend(Resource.Loading(true))
        try {
            storage.getReference(path).downloadUrl
                .addOnSuccessListener {
                    trySend(Resource.Success(it))
                }.addOnFailureListener {
                    trySend(Resource.Error(it.message ?: ERROR))
                }
        } catch (e: Exception) {
            trySend(Resource.Error(e.message ?: ERROR))
        }
        trySend(Resource.Loading(false))
        awaitClose()
    }

}