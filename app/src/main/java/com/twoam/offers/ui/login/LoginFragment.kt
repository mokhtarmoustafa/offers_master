package com.twoam.offers.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.twoam.offers.R
import com.twoam.offers.databinding.FragmentLoginBinding
import com.twoam.offers.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    //region variables
    private lateinit var binding: FragmentLoginBinding
    private  val  viewModel: LoginViewModel by viewModels()
    //endregion

    //region events

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            hideKeyboard()
            if (validateAll())
                login()
        }
        
        binding.tvRegister.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_registerFragment) }
    }

    //endregion
//region helper functions
    private fun validateAll(): Boolean {
        val email = binding.etEmail.text?.trim().toString()
        val password = binding.etPassword.text?.trim().toString()
        if (!isEmailValid(email)) {
            binding.etEmail.requestFocus()
            Toast.makeText(requireContext(), "Enter valid email", Toast.LENGTH_SHORT).show()
            return false
        } else if (!isPasswordValid(password)) {
            binding.etPassword.requestFocus()
            Toast.makeText(requireContext(), "Enter valid password", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun login() {
        val email = binding.etEmail.text?.trim().toString()
        val password = binding.etPassword.text?.trim().toString()

        viewModel.login(email, password)
        binding.progressBar.isVisible = true

        viewModel.success.observe(this, { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    if (result.data != null)
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    else
                        Toast.makeText(
                            requireContext(),
                            "You are not register yet! PLEASE Register first.",
                            Toast.LENGTH_SHORT
                        ).show()
                }
                is Resource.Failure -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        "Some thing is wrong ${result.exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {}
            }

        })
    }

    //endregion
    companion object {
        private const val TAG = "LoginFragment"
    }
}