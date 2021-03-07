package com.poittevin.francois.aurisquedevousplaire.api

import androidx.lifecycle.MutableLiveData
import com.poittevin.francois.aurisquedevousplaire.events.FailureEvent
import com.poittevin.francois.aurisquedevousplaire.models.Customer
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerHelper {

    private val apiService = ApiService
        .retrofit
        .create(
            ApiService::class.java
        )

    fun updateCustomer(customer: Customer) {

        apiService.updateCustomer(customer)
            .enqueue(object : Callback<Customer> {
                override fun onResponse(
                    call: Call<Customer>,
                    response: Response<Customer>
                ) {
                }

                override fun onFailure(call: Call<Customer>, t: Throwable) {
                    EventBus.getDefault()
                        .post(FailureEvent("une erreur s'est produite lors de la synchronisation avec le serveur"))
                }

            })
    }

    fun insertCustomer(customer: Customer) {

        apiService.insertCustomer(customer)
            .enqueue(object : Callback<Customer> {
                override fun onResponse(
                    call: Call<Customer>,
                    response: Response<Customer>
                ) {
                }

                override fun onFailure(call: Call<Customer>, t: Throwable) {
                    EventBus.getDefault()
                        .post(FailureEvent("une erreur s'est produite lors de la synchronisation avec le serveur"))
                }

            })
    }

    fun deleteCustomer(customer: Customer) {

        apiService.deleteCustomer(customer)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {}

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    EventBus.getDefault()
                        .post(FailureEvent("une erreur s'est produite lors de la synchronisation avec le serveur"))
                }
            })
    }

    fun getCustomersList(): MutableLiveData<List<Customer>> {

        val customersListLiveData = MutableLiveData<List<Customer>>()

        apiService
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
                        .post(FailureEvent("une erreur s'est produite lors de la synchronisation avec le serveur"))
                }
            })

        return customersListLiveData
    }
}