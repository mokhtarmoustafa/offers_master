package com.twoam.offers.ui.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.twoam.offers.R
import com.twoam.offers.data.model.User
import com.twoam.offers.databinding.FragmentRegisterBinding
import com.twoam.offers.util.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.xml.validation.Validator

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    //region variables
    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var binding: FragmentRegisterBinding
    //endregion

    //region events
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        getRules()

        binding.btnLogin.setOnClickListener {
            hideKeyboard()
            register()
        }
        binding.tvLogin.setOnClickListener {
            hideKeyboard()
            navigateToLogin()
        }

        binding.ivBack.setOnClickListener { findNavController().navigateUp() }
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }
    //endregion

    //region helper functions

    private fun register() {
        if (!validateAll())
            return
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val telephone = binding.etTelephone.text.toString().trim()
        val rule = binding.etRule.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val user = User(
            name = name,
            email = email,
            telephone = telephone,
            rule = rule,
            password = password
        )
        Log.d(TAG, "register: $user")
        binding.progress.isVisible = true
        viewModel.register(user)


        viewModel.success.observe(this, { result ->

            when (result) {
                is Resource.Loading -> {
                    binding.progress.isVisible = true
                }
                is Resource.Success -> {
                    binding.progress.isVisible = false
                    findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                }
                is Resource.Failure -> {
                    binding.progress.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        "Error ${result.exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        })
    }

    private fun validateAll(): Boolean {
        if (!isNotEmpty(binding.etName.text.toString().trim())) {
            Toast.makeText(requireContext(), "Name can't be empty.", Toast.LENGTH_SHORT).show()
            binding.etName.requestFocus()
            return false
        } else if (!isNotEmpty(
                binding.etEmail.text.toString().trim()
            ) || !isEmailValid(binding.etEmail.text.toString().trim())
        ) {
            Toast.makeText(requireContext(), "Enter valid Email.", Toast.LENGTH_SHORT).show()
            binding.etEmail.requestFocus()
            return false
        } else if (!isNotEmpty(binding.etTelephone.text.toString().trim())) {
            Toast.makeText(requireContext(), "Telephone can't be empty", Toast.LENGTH_SHORT).show()
            binding.etTelephone.requestFocus()
            return false
        } else if (!isNotEmpty(binding.etRule.text.toString().trim())) {
            Toast.makeText(requireContext(), "Choose Rule.", Toast.LENGTH_SHORT).show()
            binding.etEmail.requestFocus()
            return false
        } else if (!isNotEmpty(binding.etPassword.text.toString().trim()) || !isPasswordValid(
                binding.etPassword.text.toString().trim()
            )
        ) {
            Toast.makeText(requireContext(), "Enter valid password.", Toast.LENGTH_SHORT).show()
            binding.etPassword.requestFocus()
            return false
        } else if (!isNotEmpty(
                binding.etConfirmPassword.text.toString().trim()
            ) || !isPasswordValid(
                binding.etConfirmPassword.text.toString().trim()
            )
        ) {
            Toast.makeText(requireContext(), "Enter valid Confirm password.", Toast.LENGTH_SHORT)
                .show()
            binding.etConfirmPassword.requestFocus()
            return false
        } else if (binding.etPassword.text.toString()
                .trim() != binding.etConfirmPassword.text.toString().trim()
        ) {
            Toast.makeText(
                requireContext(),
                "Password and Confirm Password not the same.",
                Toast.LENGTH_SHORT
            ).show()
            binding.etConfirmPassword.requestFocus()
            return false
        }


        return true
    }

    private fun getRules() {
        val rules = requireContext().resources.getStringArray(R.array.Rules)
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, rules)
        binding.etRule.setAdapter(adapter)
    }
    //endregion

    companion object {
        private const val TAG = "RegisterFragment"
    }
}