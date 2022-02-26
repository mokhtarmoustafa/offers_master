package com.twoam.offers.ui.offer.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.twoam.offers.R

class OfferDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = OfferDetailsFragment()
    }

    private lateinit var viewModel: OfferDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(OfferDetailsViewModel::class.java)
        return inflater.inflate(R.layout.offer_details_fragment, container, false)
    }


}