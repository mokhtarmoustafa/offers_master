package com.twoam.offers.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.twoam.offers.R
import com.twoam.offers.databinding.FragmentProfileBinding
import com.twoam.offers.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    //region variables
    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
//endregion

    //region events
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //endregion
    //region helper functions
    private fun getUserData() {
        profileViewModel.getProfileData()
        profileViewModel.userData.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    binding.tvUsername.text = it.data?.name
                }
                is Resource.Failure -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), it.exception.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    private fun logOut() {
        profileViewModel.logOut()
        profileViewModel.isOut.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    findNavController().navigate(R.id.profileFragment_to_splashFragment)
                }
                is Resource.Failure -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), it.exception.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    private fun init() {
        getUserData()
        binding.btnLogout.setOnClickListener { logOut() }
    }

    //endregion
    companion object {
        private const val TAG = "ProfileFragment"
    }
}