package com.poittevin.francois.aurisquedevousplaire.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.poittevin.francois.aurisquedevousplaire.injection.Injection
import com.poittevin.francois.aurisquedevousplaire.models.Customer

abstract class BaseApiWorker(context: Context, workerParams: WorkerParameters) : Worker(context,
    workerParams
) {
    protected val customerRepository = Injection.provideCustomerRepository(context)

    companion object {
        const val CUSTOMER_ID_KEY = "CUSTOMER_ID_KEY"
    }

    protected fun getCustomerFromData(): Customer {
        val customerId = inputData.getInt(CUSTOMER_ID_KEY, 0)

        return customerRepository.getCustomerForSync(customerId)
    }
}