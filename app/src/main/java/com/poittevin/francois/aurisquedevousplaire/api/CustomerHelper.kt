package com.poittevin.francois.aurisquedevousplaire.api

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import com.poittevin.francois.aurisquedevousplaire.R
import com.poittevin.francois.aurisquedevousplaire.events.FailureEvent
import com.poittevin.francois.aurisquedevousplaire.models.Customer
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerHelper {

    private val customerService = CustomerService
        .retrofit
        .create(
            CustomerService::class.java
        )

    fun updateCustomer(customer: Customer) {

        customerService.updateCustomer(customer)
            .enqueue(object : Callback<Customer> {
                override fun onResponse(
                    call: Call<Customer>,
                    response: Response<Customer>
                ) {
                }

                override fun onFailure(call: Call<Customer>, t: Throwable) {
                    EventBus.getDefault()
                        .post(FailureEvent(Resources.getSystem().getString(R.string.server_error)))
                }

            })
    }

    fun insertCustomer(customer: Customer) {

        customerService.insertCustomer(customer)
            .enqueue(object : Callback<Customer> {
                override fun onResponse(
                    call: Call<Customer>,
                    response: Response<Customer>
                ) {
                }

                override fun onFailure(call: Call<Customer>, t: Throwable) {
                    EventBus.getDefault()
                        .post(FailureEvent(Resources.getSystem().getString(R.string.server_error)))
                }

            })
    }

    fun deleteCustomer(customer: Customer) {

        customerService.deleteCustomer(customer)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {}

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    EventBus.getDefault()
                        .post(FailureEvent(Resources.getSystem().getString(R.string.server_error)))
                }
            })
    }

    fun getCustomersList(): MutableLiveData<List<Customer>> {

        val customersListLiveData = MutableLiveData<List<Customer>>()

        customerService
            .getCustomersList()
            .enqueue(object : Callback<List<Customer>> {
                override fun onResponse(
                    call: Call<List<Customer>>,
                    response: Response<List<Customer>>
                ) {
                    response.body()?.let { customersList ->
                        customersListLiveData.value = customersList
                    }
                }

                override fun onFailure(call: Call<List<Customer>>, t: Throwable) {
                    EventBus.getDefault()
                        .post(FailureEvent(Resources.getSystem().getString(R.string.server_error)))
                }
            })

        return customersListLiveData
    }
}