package com.poittevin.francois.aurisquedevousplaire.ui.customerDetails

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.poittevin.francois.aurisquedevousplaire.models.Customer
import com.poittevin.francois.aurisquedevousplaire.repositories.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CustomerDetailsViewModel(private val customerRepository: CustomerRepository) : ViewModel() {

    companion object {
        const val MAX_NUMBER_OF_STAMPS_BY_CARD = 10
    }

    lateinit var customer: MediatorLiveData<Customer>

    fun getCustomer(id: Int) {

        Log.e("detailsViewModel", "get customer $id")

        customer = MediatorLiveData<Customer>().apply {
            addSource(customerRepository.getCustomer(id)) {
                customer.postValue(it)
            }
        }
    }

    fun addStamp() {
        customer.value?.let {
            if (it.stampsNumber == MAX_NUMBER_OF_STAMPS_BY_CARD) {
                it.stampsNumber = 1
                it.cardsNumber++
            } else {
                it.stampsNumber++
            }

            if (it.stampsNumber == MAX_NUMBER_OF_STAMPS_BY_CARD) {
                it.reductionNumber++
            }
            GlobalScope.launch(Dispatchers.IO) {
                customerRepository.updateCustomer(it)
            }
        }
    }

    fun useReduction() {
        customer.value?.let {
            it.reductionNumber--
            GlobalScope.launch(Dispatchers.IO) {
                customerRepository.updateCustomer(it)
            }
        }
    }

    fun deleteCustomer() {
        customer.value?.let {
            GlobalScope.launch(Dispatchers.IO) {
                customerRepository.deleteCustomer(it)
            }
        }
    }
}