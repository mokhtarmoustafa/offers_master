package com.twoam.offers.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.twoam.offers.ui.offer.chat.OfferChatFragment
import com.twoam.offers.ui.offer.detail.OfferDetailsFragment

class OfferViewAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> OfferDetailsFragment.newInstance()
            2 -> OfferChatFragment.newInstance()
            else -> OfferDetailsFragment.newInstance()
        }
    }
}