package com.twoam.offers.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InProgressViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is InProgress Offers Fragment"
    }
    val text: LiveData<String> = _text
}