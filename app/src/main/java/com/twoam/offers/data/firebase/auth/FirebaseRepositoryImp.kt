package com.twoam.offers.data.firebase.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.twoam.offers.data.model.Offer
import com.twoam.offers.data.model.User
import com.twoam.offers.util.OFFERS
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
    private var offersList = mutableListOf<Offer>()

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

    override suspend fun newLoginUser(email: String, password: String) =
        withContext(Dispatchers.IO) {
            safeCall {

                val user = auth.currentUser
                if (user != null) {
                    val userListener = object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            currentUser = snapshot.getValue(User::class.java)
                            Resource.Success(currentUser)
                        }

                        override fun onCancelled(error: DatabaseError) = Unit
                    }
                    db.reference.child(USERS).child(user.uid)
                        .addListenerForSingleValueEvent(userListener)
//                    Log.d(TAG, "getUserData: ${user.email}")
//                    currentUser =
//                        User(id = user.uid, name = user.displayName!!, email = user.email!!)
                    Resource.Success(currentUser)
                } else {
                    Log.d(TAG, "getUserData: NO SUCH USER EXIST")
                    Resource.Success(null)
                }
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
//        return withContext(Dispatchers.IO) {
            Resource.Loading
          return   safeCall {
                val authUser =
                    withContext(Dispatchers.Default) {
                        auth.createUserWithEmailAndPassword(user.email, user.password).await()
                    }
                if (authUser.user != null) {
                    user.id = authUser.user!!.uid
                    Log.d(TAG, "createNewUser: ${user.id} ")
                }
                db.reference.child(USERS).child(user.id).setValue(user).await()
                Resource.Success(true)
            }
//        }
    }
//        return withContext(Dispatchers.IO) {
//            Resource.Loading
//            safeCall {
//                val authUser = async {
//                    auth.createUserWithEmailAndPassword(user.email, user.password)
//                }.await()
//                if (authUser.isSuccessful && authUser.isComplete) {
//                    auth.currentUser?.updateProfile(
//                        UserProfileChangeRequest.Builder().setDisplayName(user.name)
//                            .build()
//                    )
//                    if (authUser.result.user != null) {
//                        user.id = authUser.result.user!!.uid
//                        Log.d(TAG, "createNewUser: ${user.id} ")
//                    }
//                    db.reference.child(USERS).child(user.id).setValue(user).await()
//                }
//
//                Resource.Success(true)
//            }
//        }
//    }

    override suspend fun updateUser(
        userId: String,
        nodeName: String,
        value: Any
    ): Resource<Boolean> {
        TODO("Not yet implemented")
    }


    override suspend fun getOffers(userId: String): Resource<List<Offer>> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val offerListener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        dataSnapshot.children.forEach { offerSnapShot ->
                            offerSnapShot.getValue(Offer::class.java)?.let { offersList.add(it) }
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) = Unit
                }
                db.reference.child(OFFERS).addValueEventListener(offerListener)
                Resource.Success(offersList)
            }
        }
    }


    companion object {
        private const val TAG = "FirebaseRepositoryImp"
    }
}