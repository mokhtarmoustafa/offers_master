package com.twoam.offers.ui.offer.chat

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.twoam.offers.R

class OfferChatFragment : Fragment() {

    companion object {
        fun newInstance() = OfferChatFragment()
    }

    private lateinit var viewModel: OfferChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.offer_chat_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OfferChatViewModel::class.java)
        // TODO: Use the ViewModel
    }

}