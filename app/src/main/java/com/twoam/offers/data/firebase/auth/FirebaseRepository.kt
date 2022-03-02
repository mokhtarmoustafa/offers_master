package com.twoam.offers.data.firebase.auth

import androidx.lifecycle.LiveData
import com.twoam.offers.data.model.User
import com.twoam.offers.util.Resource
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {
    suspend fun loginUser(email: String, password: String):Resource<User?>
//    suspend fun loginUser1(email: String, password: String): Flow<LiveData<Resource<User?>>>
    suspend fun createNewUser(user: User): Resource<Boolean>
    suspend fun getUserData(): Resource<User?>
    suspend fun logOut(): Resource<Boolean>
}