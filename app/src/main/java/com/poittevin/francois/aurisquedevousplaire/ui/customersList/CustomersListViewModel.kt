package com.poittevin.francois.aurisquedevousplaire.ui.customersList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.poittevin.francois.aurisquedevousplaire.models.Customer
import com.poittevin.francois.aurisquedevousplaire.repositories.CustomerRepository

class CustomersListViewModel(private val customerRepository: CustomerRepository) : ViewModel() {
    fun getCustomersList(): LiveData<List<Customer>> = customerRepository.getCustomersList()
}