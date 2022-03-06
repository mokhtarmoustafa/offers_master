package com.twoam.offers.data.firebase.auth

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseUser
import com.twoam.offers.data.model.Offer
import com.twoam.offers.data.model.User
import com.twoam.offers.util.Resource
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {
    //auth
    suspend fun loginUser(email: String, password: String, onResult: (Resource<User?>) -> Unit)
    suspend fun authNewUser(user: User, onResult: (Resource<User?>) -> Unit)
    suspend fun getUserData(onResult: (Resource<User?>) -> Unit)
    suspend fun logOut(): Resource<Boolean>

    //db
    suspend fun createNewUser(user: User, onResult: (Resource<Boolean>) -> Unit)
    suspend fun updateUser(userId: String, nodeName: String, value: Any): Resource<Boolean>
    suspend fun getOffers(userId: String, onResult: (Resource<List<Offer>>) -> Unit)


}