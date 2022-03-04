package com.twoam.offers.ui.offer.newoffer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.twoam.offers.R

class NewOfferFragment : Fragment() {

    companion object {
        fun newInstance() = NewOfferFragment()
    }

    private lateinit var viewModel: NewOfferViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.new_offer_fragment, container, false)
    }



}