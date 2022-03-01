package com.twoam.offers.ui.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twoam.offers.data.firebase.auth.FirebaseRepositoryImp
import com.twoam.offers.data.model.User
import com.twoam.offers.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val authActionManager: FirebaseRepositoryImp) :
    ViewModel() {
    private var _userData = MutableLiveData<Resource<User?>>()
    val userData: LiveData<Resource<User?>> get() = _userData


    init {
        viewModelScope.launch {
            Log.d(TAG, ": User Data: ${_userData.value}")
            val userData = authActionManager.getUserData()
            _userData.postValue(userData)


        }
    }


    companion object {
        private const val TAG = "SplashViewModel"
    }
}