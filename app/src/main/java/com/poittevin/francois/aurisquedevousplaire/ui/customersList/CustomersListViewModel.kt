package com.poittevin.francois.aurisquedevousplaire.ui.customersList

import androidx.lifecycle.*
import com.poittevin.francois.aurisquedevousplaire.models.Customer
import com.poittevin.francois.aurisquedevousplaire.repositories.CustomerRepository

class CustomersListViewModel(private val customerRepository: CustomerRepository) : ViewModel() {

    val searchLiveData = MutableLiveData<String>().apply { value = "" }
    val listChange = MutableLiveData<Boolean>().apply { value = false }

    private val customersList = MediatorLiveData<List<Customer>>().apply {
        addSource(
            Transformations.switchMap(searchLiveData) {
                if (it.isNotEmpty()) {
                    customerRepository.getCustomersList(it)
                } else {
                    customerRepository.getCustomersList(null)
                }
            }) { customers ->
            value = customers
        }
        addSource(
            Transformations.switchMap(listChange) {
                customerRepository.getCustomersList(null)

            }
        ) { customers ->
            value = customers

        }
    }


    fun getCustomersList(): LiveData<List<Customer>> = customersList
}