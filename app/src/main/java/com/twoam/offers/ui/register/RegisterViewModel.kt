package com.twoam.offers.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twoam.offers.data.firebase.auth.FirebaseRepositoryImp
import com.twoam.offers.data.model.User
import com.twoam.offers.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
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
        viewModelScope.launch {
            _success.postValue(firebaseRepositoryImp.createNewUser(user))
        }
    }
    //endregion

    companion object {
        private const val TAG = "RegisterViewModel"
    }

}