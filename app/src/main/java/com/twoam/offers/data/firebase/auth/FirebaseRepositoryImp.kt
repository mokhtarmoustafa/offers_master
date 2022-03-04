package com.twoam.offers.data.firebase.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.twoam.offers.data.model.User
import com.twoam.offers.util.Resource
import com.twoam.offers.util.USERS
import com.twoam.offers.util.safeCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FirebaseRepositoryImp @Inject constructor(
    private var auth: FirebaseAuth,
    private var db: FirebaseDatabase
) :
    FirebaseRepository {

    private var currentUser: User? = null

    override suspend fun loginUser(email: String, password: String): Resource<FirebaseUser?> =
        withContext(Dispatchers.IO) {
            Resource.Loading
            safeCall {
                Log.d(TAG, "loginUser: $email  $password")
                val result = auth.signInWithEmailAndPassword(email, password).await()
                val user = result.user
                Resource.Success(user)
            }
        }


    override suspend fun authNewUser(user: User): Resource<Boolean> =
        withContext(Dispatchers.IO) {
            Resource.Loading
            safeCall {
                Log.d(TAG, "authNewUser- Update name: ${user.name}")
                auth.createUserWithEmailAndPassword(user.email, user.password)
                    .addOnCompleteListener {
                        if (it.isSuccessful && it.isComplete) {
                            auth.currentUser?.updateProfile(
                                UserProfileChangeRequest.Builder().setDisplayName(user.name)
                                    .build()
                            )
                        }
                    }
                Resource.Success(true)
            }
        }


    override suspend fun getUserData(): Resource<User?> =
        withContext(Dispatchers.IO) {
            safeCall {
                val user = auth.currentUser
                if (user != null) {
                    Log.d(TAG, "getUserData: ${user.email}")
                    currentUser =
                        User(id = user.uid, name = user.displayName!!, email = user.email!!)
                    Resource.Success(currentUser)
                } else {
                    Log.d(TAG, "getUserData: NO SUCH USER EXIST")
                    Resource.Success(null)
                }
            }
        }

    override suspend fun logOut(): Resource<Boolean> {
        return withContext(Dispatchers.IO) {
            safeCall {
                auth.signOut()
                Resource.Success(true)
            }
        }
    }

    override suspend fun createNewUser(user: User): Resource<Boolean> {

        return withContext(Dispatchers.IO) {
            Resource.Loading
            safeCall {
                val authUser = async {
                    auth.createUserWithEmailAndPassword(user.email, user.password) }.await()
                if (authUser.isSuccessful && authUser.isComplete) {
                    auth.currentUser?.updateProfile(
                        UserProfileChangeRequest.Builder().setDisplayName(user.name)
                            .build()
                    )
                    if (authUser.result.user != null) {
                        user.id = authUser.result.user!!.uid
                        Log.d(TAG, "createNewUser: ${user.id} ")
                    }
                    db.reference.child(USERS).child(user.id).setValue(user).await()
                }

                Resource.Success(true)
            }
        }
    }

    override suspend fun updateUser(
        userId: String,
        nodeName: String,
        value: Any
    ): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    companion object {
        private const val TAG = "FirebaseRepositoryImp"
    }
}