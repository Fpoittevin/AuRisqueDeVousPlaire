package com.poittevin.francois.aurisquedevousplaire.ui.reductionDialog

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.poittevin.francois.aurisquedevousplaire.utils.Utils

class ReductionViewModel: ViewModel() {

    val price = MutableLiveData<Int>().apply {
        value = 0
    }
    val priceWithReduction = MediatorLiveData<Double>().apply {
        addSource(price) {
            value = Utils.calculateReduction(it.toDouble())
        }
    }

    fun initValues() {
        price.value = 0
    }
}