package com.twoam.offers.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.twoam.offers.data.firebase.auth.FirebaseRepositoryImp
import com.twoam.offers.data.model.Offer
import com.twoam.offers.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repositoryImp: FirebaseRepositoryImp) : ViewModel() {
    //region variables
    private var _offersData = MutableLiveData<Resource<List<Offer>>>()
    val offersList:LiveData<Resource<List<Offer>>> = _offersData
    //endregion

    //region helper functions
    init {
        getOffers()
    }

     fun getOffers() {
        viewModelScope.launch {
            val userId = FirebaseAuth.getInstance().currentUser?.uid
           val data= repositoryImp.getOffers(userId!!)
            _offersData.postValue(data)
        }
    }
    //endregion

    companion object {
        private const val TAG = "HomeViewModel"
    }

}