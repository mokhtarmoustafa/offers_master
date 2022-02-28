package com.twoam.offers.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
    private lateinit var viewModel: LoginViewModel
    //endregion

    //region events

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.btnLogin.setOnClickListener {
            if (validateAll())
                login()
        }
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
        binding.progressBar.visibility = View.VISIBLE
        viewModel.success.observe(this, Observer { result ->
            if(result)
            {
                binding.progressBar.visibility = View.INVISIBLE
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
            else
            {
                Toast.makeText(
                        requireContext(),
                        "You are not register yet! PLEASE create a new account",
                        Toast.LENGTH_SHORT
                    ).show()
            }
//            when (result) {
//                DataState.Loading -> {
//                    binding.progressBar.visibility = View.VISIBLE
//                }
//                DataState.Success(result) -> {
//                    binding.progressBar.visibility = View.INVISIBLE
//                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//                }
//                DataState.Error(result.toString()) -> {
//                    binding.progressBar.visibility = View.INVISIBLE
//                    Toast.makeText(
//                        requireContext(),
//                        "You are not register yet! PLEASE create a new account",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                else -> {
//                    binding.progressBar.visibility = View.INVISIBLE
//                }
//            }


        })
    }

    //endregion
    companion object {
        private const val TAG = "LoginFragment"
    }
}