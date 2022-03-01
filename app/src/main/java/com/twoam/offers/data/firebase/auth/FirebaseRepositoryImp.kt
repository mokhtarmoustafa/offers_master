package com.twoam.offers.data.firebase.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.twoam.offers.data.model.User
import com.twoam.offers.util.Resource
import java.lang.Exception
import javax.inject.Inject


class FirebaseRepositoryImp @Inject constructor(private val auth: FirebaseAuth) :
    FirebaseRepository {
    private var currentUser: User? = null

//
//    override suspend fun loginUser(email: String, password: String, onResult: (Boolean) -> Unit) {
//
//            auth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener {
//                    Log.d(TAG, "loginUser: ${it.isComplete && it.isSuccessful}")
//                    it.isComplete && it.isSuccessful
//                }
//
//    }
//
//    override suspend fun createNewUser(user: User, onResult: (Boolean) -> Unit) {
//        auth.createUserWithEmailAndPassword(user.email, user.password)
//            .addOnCompleteListener {
//                if (it.isComplete && it.isSuccessful) {
//                    auth.currentUser?.updateProfile(
//                        UserProfileChangeRequest
//                            .Builder()
//                            .setDisplayName(user.name)
//                            .build()
//                    )
//                    onResult(true)
//                } else {
//                    onResult(false)
//                }
//            }
//    }
//
//    override suspend fun getUserData(): User? {
//
//        val user = auth.currentUser
//        Log.d(TAG, "getUserData: $user")
//
//        user?.let {
//            currentUser = User(user.displayName!!, user.email!!, user.displayName!!)
//        }
//        return currentUser
//    }
//
//
//    override suspend fun logOut(onResult: () -> Unit) {
//        auth.signOut()
//        onResult()
//    }


    override suspend fun loginUser(email: String, password: String): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun createNewUser(user: User): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserData(): Resource<User?> {

        return try {
            val user = auth.currentUser
            if (user != null)
                Resource.Success(User(user.displayName!!, user.email!!, user.displayName!!))
            else
                Resource.Success(User())

        } catch (exception: Exception) {
            Log.d(TAG, "getUserData: ${exception.message}")
            Resource.Failure(exception.message!!)
        }

    }

    override suspend fun logOut(): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    companion object {
        private const val TAG = "FirebaseRepositoryImp"
    }
}