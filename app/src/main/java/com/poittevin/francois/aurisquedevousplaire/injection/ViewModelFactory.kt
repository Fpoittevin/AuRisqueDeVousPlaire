package com.poittevin.francois.aurisquedevousplaire.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.poittevin.francois.aurisquedevousplaire.repositories.CustomerRepository
import com.poittevin.francois.aurisquedevousplaire.ui.customerDetails.CustomerDetailsViewModel
import com.poittevin.francois.aurisquedevousplaire.ui.customerForm.CustomerFormViewModel
import com.poittevin.francois.aurisquedevousplaire.ui.customersList.CustomersListViewModel

class ViewModelFactory(private val customerRepository: CustomerRepository) :
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
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}