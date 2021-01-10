package com.poittevin.francois.aurisquedevousplaire.ui.customerForm

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.poittevin.francois.aurisquedevousplaire.models.Customer
import com.poittevin.francois.aurisquedevousplaire.repositories.CustomerRepository

class CustomerFormViewModel(private val customerRepository: CustomerRepository) : ViewModel() {

    val customer = MediatorLiveData<Customer>()
    val result = MediatorLiveData<Customer>()

    fun getCustomer(id: Int) {
        customer.addSource(customerRepository.getCustomer(id)) {
            customer.value = it
        }
    }

    fun saveCustomer() {

        customer.value?.let { customerValue ->
            customerValue.id?.let {
                result.addSource(customerRepository.updateCustomer(customerValue)) {
                    result.value = it
                }
            } ?: run {
                result.addSource(customerRepository.insertCustomer(customerValue)) {
                    result.value = it
                }
            }
        }
    }
}