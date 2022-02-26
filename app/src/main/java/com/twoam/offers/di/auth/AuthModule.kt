package com.twoam.offers.di.auth

import com.google.firebase.auth.FirebaseAuth
import com.twoam.offers.data.firebase.auth.FirebaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideFirebaseUser(auth: FirebaseAuth) = auth.currentUser!!

@Provides
@Singleton
fun provideAuthActionsManager(auth: FirebaseAuth):FirebaseRepository
{
    return FirebaseRepository(auth)
}

}