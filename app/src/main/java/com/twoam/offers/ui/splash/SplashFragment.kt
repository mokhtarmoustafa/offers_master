package com.twoam.offers.ui.splash

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.twoam.offers.R
import com.twoam.offers.data.model.User
import com.twoam.offers.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SplashFragment : Fragment() {

    //region variables
    val TIMER_DURATION = 1000L
    private lateinit var binding: FragmentSplashBinding
    private val viewModel: SplashViewModel by viewModels()

    //endregion
    //region events

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)

        object : CountDownTimer(TIMER_DURATION, 1000) {
            override fun onTick(millisUntilFinished: Long) = Unit

            override fun onFinish() = checkUserLogged()
        }.start()
        return binding.root
    }


    //endregion

    private fun checkUserLogged() {
        viewModel.userData.observe(this, Observer { userData ->
            Log.d(TAG, "onCreateView: $userData")
//            if (userData != null) {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
//            }
//            else
//                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }
    )

    }
    //region helper functions

    //endregion
    companion object {
        private const val TAG = "SplashFragment"
    }


}




