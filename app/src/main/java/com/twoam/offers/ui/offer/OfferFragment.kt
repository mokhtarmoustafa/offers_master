package com.twoam.offers.ui.offer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.twoam.offers.R

class OfferFragment : Fragment() {

    companion object {
        fun newInstance() = OfferFragment()
    }

    private lateinit var viewModel: OfferViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(OfferViewModel::class.java)
        return inflater.inflate(R.layout.offer_fragment, container, false)
    }



}