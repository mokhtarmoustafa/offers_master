package com.twoam.offers.ui.inprogress

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InProgressViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is InProgress Offers Fragment"
    }
    val text: LiveData<String> = _text
}