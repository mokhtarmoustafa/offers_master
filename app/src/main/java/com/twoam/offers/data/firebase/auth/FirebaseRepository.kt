package com.twoam.offers.data.firebase.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.twoam.offers.data.model.User
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


class FirebaseRepository(private val auth: FirebaseAuth) : AuthUserActions {
private  var currentUser:User? = null
    override fun loginUser(email: String, password: String, onResult: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                onResult(it.isComplete && it.isSuccessful)
            }


    }

    override fun createNewUser(user: User, onResult: (Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener {
                if (it.isComplete && it.isSuccessful) {
                    auth.currentUser?.updateProfile(
                        UserProfileChangeRequest
                            .Builder()
                            .setDisplayName(user.name)
                            .build()
                    )
                    onResult(true)
                } else {
                    onResult(false)
                }
            }
    }

    override fun getUserData(): User? {

        val user = auth.currentUser
        Log.d(TAG, "getUserData: $user")

        user?.let {
                currentUser = User(user.displayName!!, user.email!!, user.displayName!!)
        }
        return currentUser
    }


    override fun logOut(onResult: () -> Unit) {
        auth.signOut()
        onResult()
    }


    companion object {
        private const val TAG = "FirebaseRepository"
    }
}