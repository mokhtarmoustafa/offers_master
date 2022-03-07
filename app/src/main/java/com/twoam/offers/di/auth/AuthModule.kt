package com.twoam.offers.di.auth

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.twoam.offers.data.firebase.auth.FirebaseRepository
import com.twoam.offers.data.firebase.auth.FirebaseRepositoryImp
import com.twoam.offers.util.PREF_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AuthModule {

    @Provides
    @Singleton
    fun provideAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }


    @Provides
    @Singleton
    fun provideAuthActionsManager(auth: FirebaseAuth, db: FirebaseDatabase): FirebaseRepository {
        return FirebaseRepositoryImp(auth, db)
    }

    @Provides
    @Singleton
    fun provideDb(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }


}