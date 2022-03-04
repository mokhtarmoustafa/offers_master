package com.twoam.offers.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.twoam.offers.R
import com.twoam.offers.adapter.OffersAdapter
import com.twoam.offers.data.model.Offer
import com.twoam.offers.databinding.FragmentHomeBinding
import com.twoam.offers.util.EMPLOYEE
import com.twoam.offers.util.MANAGER
import com.twoam.offers.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), OffersAdapter.Interaction {
    //region variables
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var adapter: OffersAdapter = OffersAdapter(this)
    private val args:HomeFragmentArgs by navArgs()
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
        binding.btnAddOffer.isVisible= args.user.type == MANAGER

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = OffersAdapter(this)

//        getOffers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemSelected(position: Int, offer: Offer) {
        val actions = HomeFragmentDirections.actionHomeFragmentToOfferFragment(offer)
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
                    binding.tvEmptyData.isVisible=false
                    adapter.submitList(it.data)
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), it.exception.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    private fun newOffer()
    {
        findNavController().navigate(R.id.action_homeFragment_to_newOfferFragment)
    }
    //endregion

    companion object {
        private const val TAG = "HomeFragment"
    }
}