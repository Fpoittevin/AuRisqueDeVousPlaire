package com.poittevin.francois.aurisquedevousplaire.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.poittevin.francois.aurisquedevousplaire.repositories.CustomerRepository
import com.poittevin.francois.aurisquedevousplaire.repositories.MessageRepository
import com.poittevin.francois.aurisquedevousplaire.ui.customerDetails.CustomerDetailsViewModel
import com.poittevin.francois.aurisquedevousplaire.ui.customerForm.CustomerFormViewModel
import com.poittevin.francois.aurisquedevousplaire.ui.customersList.CustomersListViewModel
import com.poittevin.francois.aurisquedevousplaire.ui.message.MessageViewModel
import com.poittevin.francois.aurisquedevousplaire.ui.reductionDialog.ReductionViewModel

class ViewModelFactory(private val customerRepository: CustomerRepository, private val messageRepository: MessageRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CustomersListViewModel::class.java) -> {
                CustomersListViewModel(customerRepository) as T
            }
            modelClass.isAssignableFrom(CustomerDetailsViewModel::class.java) -> {
                CustomerDetailsViewModel(customerRepository) as T
            }
            modelClass.isAssignableFrom(CustomerFormViewModel::class.java) -> {
                CustomerFormViewModel(customerRepository) as T
            }
            modelClass.isAssignableFrom(MessageViewModel::class.java) -> {
                MessageViewModel(customerRepository, messageRepository) as T
            }
            modelClass.isAssignableFrom(ReductionViewModel::class.java) -> {
                ReductionViewModel() as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}