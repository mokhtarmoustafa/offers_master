package com.twoam.offers.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.twoam.offers.R
import com.twoam.offers.data.model.Offer
import com.twoam.offers.databinding.ListOfferItemBinding

class OffersAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Offer>() {

        override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.description == newItem.description &&
                    oldItem.salary == newItem.salary &&
                    oldItem.equite == newItem.equite &&
                    oldItem.bonus == newItem.bonus &&
                    oldItem.culture == newItem.culture &&
                    oldItem.learning == newItem.learning &&
                    oldItem.role == newItem.role &&
                    oldItem.team_details == newItem.team_details &&
                    oldItem.organization_details == newItem.organization_details &&
                    oldItem.offer_status == newItem.offer_status

        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return OfferViewHolder(
            ListOfferItemBinding.inflate(LayoutInflater.from(parent.context)),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is OfferViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Offer>) {
        differ.submitList(list)
    }

    class OfferViewHolder
    constructor(
        private val binding: ListOfferItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(offer: Offer) = with(binding) {
            binding.root.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, offer)
            }
            binding.tvDescriptionData.text = offer.description
            binding.tvOrganizationData.text = offer.organization_details
            binding.tvSalaryData.text = offer.salary
            binding.tvStatus.text = offer.offer_status
        }
    }


    interface Interaction {
        fun onItemSelected(position: Int, item: Offer)
    }
}

