package com.poittevin.francois.aurisquedevousplaire.workers

import android.content.Context
import androidx.work.WorkerParameters

class InsertCustomerWorker(
    context: Context,
    params: WorkerParameters
) :
    BaseApiWorker(context, params) {

    override fun doWork(): Result {
        val customer = getCustomerFromData()
        customerRepository.insertCustomerFromServer(customer)

        return Result.success()
    }
}