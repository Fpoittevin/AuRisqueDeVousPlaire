package com.poittevin.francois.aurisquedevousplaire.ui.customerForm

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.poittevin.francois.aurisquedevousplaire.models.Customer
import com.poittevin.francois.aurisquedevousplaire.repositories.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CustomerFormViewModel(private val customerRepository: CustomerRepository) : ViewModel() {

    lateinit var customer: MutableLiveData<Customer>

    val saveResult = MutableLiveData<Boolean>().apply { value = false }

    fun getCustomer(id: Int) {
        customer = MediatorLiveData<Customer>().apply {
            addSource(customerRepository.getCustomer(id)) {
                customer.postValue(it)
            }
        }
    }

    fun saveCustomer() {

        customer.value?.let { customerValue ->
            customerValue.id?.let {
                GlobalScope.launch(Dispatchers.IO) {
                    customerRepository.updateCustomer(customerValue)
                }
            } ?: run {
                GlobalScope.launch(Dispatchers.IO) {
                    customerRepository.insertCustomer(customerValue)
                    saveResult.postValue(true)
                }
            }
        }
    }
}