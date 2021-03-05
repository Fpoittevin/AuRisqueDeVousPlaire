package com.poittevin.francois.aurisquedevousplaire.workers

import android.content.Context
import androidx.work.WorkerParameters

class UpdateCustomerWorker(
    val context: Context,
    params: WorkerParameters
) :
    BaseApiWorker(context, params) {

    override fun doWork(): Result {
        val customer = getCustomerFromData()
        customerRepository.updateCustomerFromServer(customer)

        return Result.success()
    }
}