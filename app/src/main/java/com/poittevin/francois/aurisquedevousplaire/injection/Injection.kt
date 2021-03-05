package com.poittevin.francois.aurisquedevousplaire.injection

import android.content.Context
import com.poittevin.francois.aurisquedevousplaire.database.ArdvpDatabase
import com.poittevin.francois.aurisquedevousplaire.repositories.CustomerRepository

class Injection {

    companion object {
        fun provideCustomerRepository(context: Context): CustomerRepository {
            val db = ArdvpDatabase.getInstance(context)
            return CustomerRepository(db.customerDao(), context)
        }

        fun provideViewModelFactory(context: Context) = ViewModelFactory(
            provideCustomerRepository(context)
        )
    }
}