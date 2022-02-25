package com.twoam.offers.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.twoam.offers.R

class SplashFragment : Fragment() {

    //region variables
    val TIMER_DURATION=1000L
    //endregion
    //region events
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }


    //endregion

    //region helper functions

    //endregion
    companion object {
        private const val TAG = "SplashFragment"
    }
}