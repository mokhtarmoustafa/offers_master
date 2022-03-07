package com.twoam.offers.data.firebase.auth

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.twoam.offers.R
import com.twoam.offers.data.model.Offer
import com.twoam.offers.data.model.User
import com.twoam.offers.util.OFFERS
import com.twoam.offers.util.Resource
import com.twoam.offers.util.USERS
import com.twoam.offers.util.safeCall
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.Exception


class FirebaseRepositoryImp @Inject constructor(
    private var auth: FirebaseAuth,
    private var db: FirebaseDatabase, @ApplicationContext var context: Context
) :
    FirebaseRepository {

    private var currentUser: User? = null
    private var offersList = mutableListOf<Offer>()

    override suspend fun loginUser(
        email: String,
        password: String,
        onResult: (Resource<User?>) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            auth.currentUser?.let {
                db.reference.child(USERS).child(it.uid)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            currentUser = snapshot.getValue(User::class.java)
                            Log.d(TAG, "onDataChange-currentUser: $currentUser")
                            onResult(Resource.Success(currentUser))
                        }

                        override fun onCancelled(error: DatabaseError) = Unit
                    })
            }
            if (auth.currentUser == null) {
                Log.d(TAG, task.exception?.message ?: "")
                onResult(Resource.Failure(Exception(context.resources.getString(R.string.no_user))))
            }


        }

    }

    override suspend fun authNewUser(user: User, onResult: (Resource<User?>) -> Unit) {
        Resource.Loading
        auth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener {
            auth.currentUser.let { authUser ->
                authUser?.updateProfile(
                    UserProfileChangeRequest.Builder().setDisplayName(user.name)
                        .build()
                )
                onResult(Resource.Success(user))
            }

            if (auth.currentUser == null)
                onResult(Resource.Failure(Exception(context.resources.getString(R.string.no_user))))


        }.addOnFailureListener { exception ->
            onResult(Resource.Failure(exception))
        }


    }


    override suspend fun getUserData(onResult: (Resource<User?>) -> Unit) {
        auth.currentUser.let { user ->
            user?.let {
                db.reference.child(USERS).child(it.uid)
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
            }


        }
        if (auth.currentUser == null)
            onResult(Resource.Failure(Exception(context.getString(R.string.no_user))))

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
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { authResult ->

                authResult.result.user.let {
                    it?.let { firebaseUser ->
                        db.reference.child(USERS).child(firebaseUser.uid).setValue(user)
                            .addOnCompleteListener {
                                onResult(Resource.Success(true))
                            }.addOnFailureListener { exception ->
                            onResult(Resource.Failure(exception))
                        }
                    }
                }
                if (authResult.result.user == null)
                    onResult(Resource.Failure(Exception(context.getString(R.string.no_user))))

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