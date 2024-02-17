package com.example.myinsta.di

import android.content.Context
import com.example.myinsta.BaseApplication
import com.example.myinsta.common.Constants.BASE_URL
import com.example.myinsta.repository.FirebaseRepo.FirebaseRepository
import com.example.myinsta.repository.FirebaseRepo.FirebaseRepositoryImpl
import com.example.myinsta.repository.authRepo.AuthRepository
import com.example.myinsta.repository.authRepo.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Provides
    fun provideFirebaseAuth()
    : FirebaseAuth = FirebaseAuth.getInstance()

    /*@Singleton
    @Provides
    fun providesAuthRepository(
        impl: AuthRepositoryImpl,
        @ApplicationContext appContext: Context // Use @ApplicationContext to get the application context
    )
    : AuthRepository = impl*/
    @Singleton
    @Provides
    fun providesAuthRepository(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore,
        firebaseStorage: FirebaseStorage,
        @ApplicationContext appContext: Context // Use @ApplicationContext to get the application context
    ): AuthRepository {
        return AuthRepositoryImpl(
            firebaseAuth,
            firebaseFirestore,
            firebaseStorage,
            appContext
        )
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        firestore: FirebaseFirestore
    ): FirebaseRepository {
        return FirebaseRepositoryImpl(firestore)
    }

    @Singleton
    @Provides
    fun provideFireStore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
    @Singleton
    @Provides
    fun providesFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance(BASE_URL)
    }
}