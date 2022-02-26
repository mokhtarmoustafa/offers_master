package com.twoam.offers.data.firebase.auth

import com.twoam.offers.data.model.User

interface AuthUserActions {
    fun loginUser(email: String, password: String, onResult: (Boolean) -> Unit)
    fun createNewUser(user: User, onResult: (Boolean) -> Unit)
    fun getUserData(): User?
    fun logOut(onResult: () -> Unit)
}