package com.poittevin.francois.aurisquedevousplaire.ui.customersList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.poittevin.francois.aurisquedevousplaire.databinding.RecyclerViewCustomerItemBinding
import com.poittevin.francois.aurisquedevousplaire.models.Customer

class CustomersAdapter(
    private val customerItemClickCallback: CustomerItemClickCallback
) : RecyclerView.Adapter<CustomerViewHolder>() {

    private var customersList: MutableList<Customer> = ArrayList()

    fun updateCustomersList(list: List<Customer>) {
        customersList.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = RecyclerViewCustomerItemBinding.inflate(inflater, parent, false)

        return CustomerViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val customer = customersList[position]
        holder.bind(customer)


        customer.id?.let { id ->
            holder.itemView.setOnClickListener {
                customerItemClickCallback.onCustomerItemClick(id)
            }
        }
    }

    override fun getItemCount() = customersList.size

    interface CustomerItemClickCallback {
        fun onCustomerItemClick(customerId: Int)
    }
}