package com.example.myinsta.repository.storageRepo

import android.net.Uri
import com.example.myinsta.response.Resource
import kotlinx.coroutines.flow.Flow

interface StorageRepository {
    suspend fun uploadImg(uri: Uri, path: String)
            : Flow<Resource<Boolean>>

    suspend fun getImg(path: String)
            : Flow<Resource<Uri>>
}