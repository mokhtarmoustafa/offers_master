package com.twoam.offers.data.firebase.auth

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseUser
import com.twoam.offers.data.model.Offer
import com.twoam.offers.data.model.User
import com.twoam.offers.util.Resource
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {
    //auth
    suspend fun loginUser(email: String, password: String):Resource<FirebaseUser?>
    suspend fun authNewUser(user: User): Resource<Boolean>
    suspend fun getUserData(): Resource<User?>
    suspend fun logOut(): Resource<Boolean>
    //db
    suspend fun createNewUser(user:User):Resource<Boolean>
    suspend fun updateUser(userId:String,nodeName:String,value:Any):Resource<Boolean>
    suspend fun getOffers(userId:String):Resource<List<Offer>>



}