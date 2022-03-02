package com.twoam.offers.data.firebase.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.twoam.offers.data.model.User
import com.twoam.offers.util.Resource
import com.twoam.offers.util.safeCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FirebaseRepositoryImp @Inject constructor(private val auth: FirebaseAuth) :
    FirebaseRepository {
    private var currentUser: User? = null



    override suspend fun loginUser(email: String, password: String): Resource<User?> =
        withContext(Dispatchers.IO) {
            Resource.Loading
            safeCall {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                val user = result.user
                Log.d(TAG, "loginUser: ${user?.email}")
                currentUser = User(user?.uid!!, user.displayName!!, user.email!!)
                Resource.Success(currentUser)
            }
        }


    override suspend fun createNewUser(user: User): Resource<Boolean> {
        TODO("Not yet implemented")
    }


    override suspend fun getUserData(): Resource<User?> =
        withContext(Dispatchers.IO) {
            safeCall {
                val user = auth.currentUser
                if (user != null) {
                    Log.d(TAG, "getUserData: ${user.email}")
                    currentUser = User(user.displayName!!, user.email!!, user.displayName!!)
                    Resource.Success(currentUser)
                } else {
                    Log.d(TAG, "getUserData: NO SUCH USER EXIST")
                    Resource.Success(null)
                }
            }
        }

    override suspend fun logOut(): Resource<Boolean> {
      return  withContext(Dispatchers.IO) {
            safeCall {
                auth.signOut()
                Resource.Success(true)
            }
        }
    }

    companion object {
        private const val TAG = "FirebaseRepositoryImp"
    }
}