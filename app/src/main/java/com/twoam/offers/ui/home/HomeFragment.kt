package com.twoam.offers.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.twoam.offers.R
import com.twoam.offers.adapter.OffersAdapter
import com.twoam.offers.data.model.Offer
import com.twoam.offers.databinding.FragmentHomeBinding
import com.twoam.offers.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), OffersAdapter.Interaction {
    //region variables
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var adapter: OffersAdapter = OffersAdapter(this)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = OffersAdapter(this)
        getOffers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemSelected(position: Int, offer: Offer) {
        val actions = HomeFragmentDirections.actionNavigationHomeToOfferDetailsFragment(offer)
        findNavController().navigate(actions)
    }

    //endregion


    //region helper functions
    private fun getOffers() {
        homeViewModel.getOffers()
        homeViewModel.offersList.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    adapter.submitList(it.data)
                }
                is Resource.Failure -> {}
            }
        })
    }
    //endregion

    companion object {
        private const val TAG = "HomeFragment"
    }
}