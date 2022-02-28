package com.twoam.offers.ui.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twoam.offers.data.firebase.auth.FirebaseRepository
import com.twoam.offers.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(authActionManager: FirebaseRepository) : ViewModel() {
    private var _userData = MutableLiveData<User>()
    val userData: LiveData<User> get() = _userData


    init {
        viewModelScope.launch {
            _userData.value = authActionManager.getUserData()
            Log.d(TAG, ": User Data: ${_userData.value}")
        }

    }


    companion object {
        private const val TAG = "SplashViewModel"
    }
}