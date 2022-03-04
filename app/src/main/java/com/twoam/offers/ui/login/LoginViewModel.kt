package com.twoam.offers.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.twoam.offers.data.firebase.auth.FirebaseRepositoryImp
import com.twoam.offers.data.model.User
import com.twoam.offers.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val firebaseRepositoryImp: FirebaseRepositoryImp) :
    ViewModel() {

    //region variables
    private var _success = MutableLiveData<Resource<FirebaseUser?>>()
    val success: LiveData<Resource<FirebaseUser?>> get() = _success
    //endregion


    //region helper functions

    fun login(email: String, password: String) {

        viewModelScope.launch (Dispatchers.Main){
          val user=  firebaseRepositoryImp.loginUser(email, password)
            Log.d(TAG, "login: $user")
            _success.postValue(user)
        }
    }


    //endregion

    companion object {
        private const val TAG = "LoginViewModel"
    }
}