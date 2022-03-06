package com.twoam.offers.ui.profile

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
class ProfileViewModel  @Inject constructor(private val repositoryImp: FirebaseRepositoryImp) : ViewModel() {


    private var _userData = MutableLiveData<Resource<User?>>()
    val userData: LiveData<Resource<User?>> = _userData
    private var _isOut = MutableLiveData<Resource<Boolean> >()
    val isOut: LiveData<Resource<Boolean> > = _isOut

    fun getProfileData()
    {
       viewModelScope.launch {
          repositoryImp.getUserData{
              _userData.postValue(it)
         }

       }
    }

    fun logOut()
    {
        viewModelScope.launch {
            val done=repositoryImp.logOut()
            _isOut.postValue(done)
        }
    }
}