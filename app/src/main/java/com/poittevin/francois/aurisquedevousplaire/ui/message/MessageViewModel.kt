package com.poittevin.francois.aurisquedevousplaire.ui.message

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.poittevin.francois.aurisquedevousplaire.repositories.CustomerRepository

class MessageViewModel(customerRepository: CustomerRepository) : ViewModel() {

    val fromToNumberOfCards = customerRepository.getMinMaxNumberOfCards()

    val minMax = MediatorLiveData<Array<Int>>().apply {
        addSource(fromToNumberOfCards) {
            value = it
        }
    }

    val numberOfCustomers = MediatorLiveData<Int>().apply {
        addSource(
            Transformations.switchMap(minMax) {
                customerRepository.getNumberOfCustomersInRangeCardsNumber(it[0], it[1])
            }) { number ->
            value = number
        }
    }
}