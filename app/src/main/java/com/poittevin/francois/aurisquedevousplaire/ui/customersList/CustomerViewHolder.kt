package com.poittevin.francois.aurisquedevousplaire.ui.customersList

import androidx.recyclerview.widget.RecyclerView
import com.poittevin.francois.aurisquedevousplaire.databinding.RecyclerViewCustomerItemBinding
import com.poittevin.francois.aurisquedevousplaire.models.Customer

class CustomerViewHolder(private val binding: RecyclerViewCustomerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(customer: Customer) {
        binding.customer = customer
    }
}