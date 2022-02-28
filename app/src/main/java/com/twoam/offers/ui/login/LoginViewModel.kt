package com.twoam.offers.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twoam.offers.data.firebase.auth.FirebaseRepository
import com.twoam.offers.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    ViewModel() {

    //region variables
    private var _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> get() = _success
    //endregion


    //region helper functions

    fun login(email: String, password: String) {
        DataState.Loading
        viewModelScope.launch {
            firebaseRepository.loginUser(email, password){result->
                Log.d(TAG, "login: $result")
                _success.value=result
            }
        }
    }
    //endregion

    companion object {
        private const val TAG = "LoginViewModel"
    }
}