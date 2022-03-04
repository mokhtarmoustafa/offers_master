package com.twoam.offers.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.twoam.offers.R
import com.twoam.offers.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    //region variables
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    //endregion

    //region events
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val navView = activity?.findViewById(R.id.nav_view) as BottomNavigationView
        navView.visibility = View.VISIBLE

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //endregion


    //region helper functions

    //endregion

    companion object {
        private const val TAG = "HomeFragment"
    }
}