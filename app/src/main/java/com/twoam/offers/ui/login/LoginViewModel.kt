package com.twoam.offers.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twoam.offers.data.firebase.auth.FirebaseRepositoryImp
import com.twoam.offers.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val firebaseRepositoryImp: FirebaseRepositoryImp) :
    ViewModel() {

    //region variables
    private var _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> get() = _success
    //endregion


    //region helper functions

    fun login(email: String, password: String) {

//        viewModelScope.launch {
//            firebaseRepositoryImp.loginUser(email, password){ result->
//                Log.d(TAG, "login: $result")
//                _success.value=result
//            }
//        }
    }
    //endregion

    companion object {
        private const val TAG = "LoginViewModel"
    }
}