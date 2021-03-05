package com.poittevin.francois.aurisquedevousplaire.workers

import android.content.Context
import androidx.work.WorkerParameters
import com.poittevin.francois.aurisquedevousplaire.models.Customer

class DeleteCustomerWorker(
    context: Context,
    params: WorkerParameters
) :
    BaseApiWorker(context, params) {

    override fun doWork(): Result {
        val customer = Customer().apply {
            id = inputData.getInt(CUSTOMER_ID_KEY, 0)
        }
        customerRepository.deleteCustomerFromServer(customer)

        return Result.success()
    }
}