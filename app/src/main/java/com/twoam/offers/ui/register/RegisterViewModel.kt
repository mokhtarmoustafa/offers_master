package com.twoam.offers.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.twoam.offers.data.firebase.auth.FirebaseRepositoryImp
import com.twoam.offers.data.model.User
import com.twoam.offers.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val firebaseRepositoryImp: FirebaseRepositoryImp) :
    ViewModel() {


    //region variables
    private var _success = MutableLiveData<Resource<Boolean>>()
    val success: LiveData<Resource<Boolean>> get() = _success
    //endregion

    //region helper functions
    fun register(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            val done = firebaseRepositoryImp.createNewUser(user)
            _success.postValue(done)

        }
    }
    //endregion

    companion object {
        private const val TAG = "RegisterViewModel"
    }

}