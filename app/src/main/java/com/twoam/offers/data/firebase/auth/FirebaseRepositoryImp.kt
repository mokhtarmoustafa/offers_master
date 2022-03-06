package com.twoam.offers.data.firebase.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
import java.lang.Exception
import javax.inject.Inject


class FirebaseRepositoryImp @Inject constructor(
    private var auth: FirebaseAuth,
    private var db: FirebaseDatabase
) :
    FirebaseRepository {

    private var currentUser: User? = null
    private var offersList = mutableListOf<Offer>()

    override suspend fun loginUser(
        email: String,
        password: String,
        onResult: (Resource<User?>) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {

            if (it.isComplete && it.isSuccessful) {
                db.reference.child(USERS).child(auth.currentUser!!.uid)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            currentUser = snapshot.getValue(User::class.java)
                            Log.d(TAG, "onDataChange-currentUser: $currentUser")
                            onResult(Resource.Success(currentUser))
                        }

                        override fun onCancelled(error: DatabaseError) = Unit
                    })

            } else
                onResult(Resource.Failure(it.exception!!))


        }

    }

    override suspend fun authNewUser(user: User, onResult: (Resource<User?>) -> Unit) {
        Resource.Loading
        Log.d(TAG, "authNewUser- Update name: ${user.name}")
        auth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener {
            if (it.isComplete && it.isSuccessful) {
                auth.currentUser?.updateProfile(
                    UserProfileChangeRequest.Builder().setDisplayName(user.name)
                        .build()
                )
                onResult(Resource.Success(user))
            } else
                onResult(Resource.Failure(it.exception!!))
        }.addOnFailureListener { exception ->
            onResult(Resource.Failure(exception))
        }


    }


    override suspend fun getUserData(onResult: (Resource<User?>) -> Unit) {
        val user = auth.currentUser
        if (user != null) {
            Log.d(TAG, "getUserData: ${user.email}")
            db.reference.child(USERS).child(user.uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        currentUser = snapshot.getValue(User::class.java)
                        Log.d(TAG, "onDataChange-currentUser: $currentUser")
                        onResult(Resource.Success(currentUser))
                    }

                    override fun onCancelled(error: DatabaseError) {
                        onResult(Resource.Success(null))
                    }
                })

        } else {
            Log.d(TAG, "getUserData: NO SUCH USER EXIST")
            onResult(Resource.Success(null))
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


    override suspend fun createNewUser(user: User, onResult: (Resource<Boolean>) -> Unit) {
        Resource.Loading
        auth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener {
            if (it.isSuccessful && it.isComplete) {
                user.let { _ ->
                    db.reference.child(USERS).child(it.result.user!!.uid).setValue(user)
                }
                    .addOnCompleteListener {
                        onResult(Resource.Success(true))
                    }.addOnFailureListener { exception ->
                        onResult(Resource.Failure(exception))
                    }


            } else
                onResult(Resource.Failure(it.exception!!))
        }
    }

    override suspend fun updateUser(
        userId: String,
        nodeName: String,
        value: Any
    ): Resource<Boolean> {
        return Resource.Success(true)
    }


    override suspend fun getOffers(userId: String, onResult: (Resource<List<Offer>>) -> Unit) {

        val offerListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach { offerSnapShot ->
                    offerSnapShot.getValue(Offer::class.java)?.let { offersList.add(it) }
                }
                onResult(Resource.Success(offersList))
            }

            override fun onCancelled(databaseError: DatabaseError) {
                onResult(Resource.Failure(databaseError.toException()))
            }
        }
        db.reference.child(OFFERS).addValueEventListener(offerListener)


    }


    companion object {
        private const val TAG = "FirebaseRepositoryImp"
    }
}