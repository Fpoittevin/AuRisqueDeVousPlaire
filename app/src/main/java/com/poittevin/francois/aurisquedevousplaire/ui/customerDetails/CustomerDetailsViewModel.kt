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
            it.id?.let { id ->
                customer.addSource(
                    customerRepository.addStamp(id)
                ) { newCustomer ->
                    customer.value = newCustomer
                }
            }
        }
    }

    fun useReduction() {
        customer.value?.let {
            it.id?.let { id ->
                customer.addSource(
                    customerRepository.useReduction(id)
                ) { newCustomer ->
                    customer.value = newCustomer
                }
            }
        }
    }
}