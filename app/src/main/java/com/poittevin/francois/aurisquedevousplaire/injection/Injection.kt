package com.poittevin.francois.aurisquedevousplaire.injection

import com.poittevin.francois.aurisquedevousplaire.repositories.CustomerRepository

class Injection {

    companion object {
        private fun provideCustomerRepository() = CustomerRepository()

        fun provideViewModelFactory() = ViewModelFactory(
            provideCustomerRepository()
        )
    }
}