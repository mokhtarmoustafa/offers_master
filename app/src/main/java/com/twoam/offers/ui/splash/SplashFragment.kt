package com.twoam.offers.ui.splash

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.twoam.offers.R
import com.twoam.offers.databinding.FragmentSplashBinding
import com.twoam.offers.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    //region variables
    val TIMER_DURATION = 1000L
    private lateinit var binding: FragmentSplashBinding
    private val viewModel: SplashViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        FirebaseAuth.getInstance().signOut()
        binding = FragmentSplashBinding.inflate(inflater, container, false)

        object : CountDownTimer(TIMER_DURATION, 1000) {
            override fun onTick(millisUntilFinished: Long) = Unit

            override fun onFinish() = checkUserLogged()
        }.start()

        return binding.root
    }


    //endregion

    private fun checkUserLogged() {
        viewModel.userData.observe(this, Observer { result ->
            Log.d(TAG, "onCreateView: $result")
            when (result) {
                is Resource.Success -> {
                    if (null== result.data)
                        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                    else
                    {
                        val action=SplashFragmentDirections.actionSplashFragmentToHomeFragment(result.data)
                        findNavController().navigate(action)
                    }
                    Log.d(TAG, "onCreateView: ${result.data}")

                }
                is Resource.Failure -> {
                    Log.d(TAG, "onCreateView: ${result.exception.message}")
                    Toast.makeText(
                        requireContext(),
                        "No user Exist ${result.exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                }
                else -> {}
            }


        }
        )

    }
    //region helper functions

    //endregion
    companion object {
        private const val TAG = "SplashFragment"
    }


}




