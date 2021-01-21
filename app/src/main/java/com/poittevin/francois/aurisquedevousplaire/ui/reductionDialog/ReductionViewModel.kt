package com.poittevin.francois.aurisquedevousplaire.ui.reductionDialog

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.poittevin.francois.aurisquedevousplaire.utils.Utils

class ReductionViewModel: ViewModel() {

    val price = MutableLiveData<Float>().apply {
        value = 0F
    }
    val priceWithReduction = MediatorLiveData<Float>().apply {
        addSource(price) {
            value = Utils.calculateReduction(it)
        }
    }
}