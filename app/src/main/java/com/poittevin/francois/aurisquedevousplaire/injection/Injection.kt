package com.poittevin.francois.aurisquedevousplaire.injection

import android.content.Context
import com.poittevin.francois.aurisquedevousplaire.database.ArdvpDatabase
import com.poittevin.francois.aurisquedevousplaire.repositories.CustomerRepository
import com.poittevin.francois.aurisquedevousplaire.repositories.MessageRepository

class Injection {

    companion object {
        fun provideCustomerRepository(context: Context): CustomerRepository {
            val db = ArdvpDatabase.getInstance(context)
            return CustomerRepository(db.customerDao(), context)
        }

        private fun provideMessageRepository(): MessageRepository {
            return MessageRepository()
        }

        fun provideViewModelFactory(context: Context) = ViewModelFactory(
            provideCustomerRepository(context),
            provideMessageRepository()
        )
    }
}