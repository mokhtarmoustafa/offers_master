package com.twoam.offers.data.firebase.auth

import com.twoam.offers.data.model.User
import com.twoam.offers.util.Resource

interface FirebaseRepository {
    suspend fun loginUser(email: String, password: String): Resource<Boolean>
    suspend fun createNewUser(user: User): Resource<Boolean>
    suspend fun getUserData(): Resource<User?>
    suspend fun logOut(): Resource<Boolean>
}