package com.poittevin.francois.aurisquedevousplaire.ui.customerDetails

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.poittevin.francois.aurisquedevousplaire.models.Customer
import com.poittevin.francois.aurisquedevousplaire.repositories.CustomerRepository

class CustomerDetailsViewModel(private val customerRepository: CustomerRepository) : ViewModel() {

    val customer = MediatorLiveData<Customer>()

    fun getCustomer(id: Int) {
        customer.addSource(customerRepository.getCustomer(id)) {
            customer.value = it
        }
    }

    fun addStamp() {
        customer.value?.let {
                customer.addSource(
                    customerRepository.addStamp(it.id)) { newCustomer ->
                    customer.value = newCustomer
                }
        }
    }
}