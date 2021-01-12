package com.poittevin.francois.aurisquedevousplaire.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.poittevin.francois.aurisquedevousplaire.api.CustomerService
import com.poittevin.francois.aurisquedevousplaire.models.Customer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerRepository {

    fun getCustomer(id: Int): MutableLiveData<Customer> {

        val customerLiveData = MutableLiveData<Customer>()

        CustomerService
            .retrofit
            .create(
                CustomerService::class.java
            ).getCustomer(id)
            .enqueue(object : Callback<Customer> {
                override fun onResponse(
                    call: Call<Customer>,
                    response: Response<Customer>
                ) {
                    response.body()?.let { customer ->
                        customerLiveData.value = customer
                    }
                }

                override fun onFailure(call: Call<Customer>, t: Throwable) {
                    Log.e("error", t.message!!)
                }

            })
        return customerLiveData
    }

    fun getCustomersList(search: String?): MutableLiveData<List<Customer>> {

        val customersListLiveData = MutableLiveData<List<Customer>>()
        val customerService = CustomerService
            .retrofit
            .create(
                CustomerService::class.java
            )

        val callback = object :  Callback<List<Customer>> {
            override fun onResponse(
                call: Call<List<Customer>>,
                response: Response<List<Customer>>
            ) {
                response.body()?.let { customersList ->
                    customersListLiveData.value = customersList
                }
            }

            override fun onFailure(call: Call<List<Customer>>, t: Throwable) {
                Log.e("error", t.message!!)
            }
        }

        search?.let {
            customerService
                .searchCustomers(mapOf("searchString" to it))
                .enqueue(callback)

        } ?: run {
            customerService
                .getCustomersList()
                .enqueue(callback)
        }

        return customersListLiveData
    }

    fun updateCustomer(customer: Customer): MutableLiveData<Customer> {

        val customerLiveData = MutableLiveData<Customer>()

        CustomerService
            .retrofit
            .create(
                CustomerService::class.java
            ).updateCustomer(customer)
            .enqueue(object : Callback<Customer> {
                override fun onResponse(
                    call: Call<Customer>,
                    response: Response<Customer>
                ) {
                    response.body()?.let { customer ->
                        customerLiveData.value = customer
                    }
                }

                override fun onFailure(call: Call<Customer>, t: Throwable) {
                    Log.e("call", call.toString())
                    Log.e("error", t.message!!)
                }

            })
        return customerLiveData
    }

    fun insertCustomer(customer: Customer): MutableLiveData<Customer> {

        val customerLiveData = MutableLiveData<Customer>()

        CustomerService
            .retrofit
            .create(
                CustomerService::class.java
            ).insertCustomer(customer)
            .enqueue(object : Callback<Customer> {
                override fun onResponse(
                    call: Call<Customer>,
                    response: Response<Customer>
                ) {
                    response.body()?.let { customer ->
                        customerLiveData.value = customer
                    }
                }

                override fun onFailure(call: Call<Customer>, t: Throwable) {
                    Log.e("call", call.toString())
                    Log.e("error", t.message!!)
                }

            })
        return customerLiveData
    }

    fun addStamp(id: Int): MutableLiveData<Customer> {

        val customerLiveData = MutableLiveData<Customer>()

        CustomerService
            .retrofit
            .create(
                CustomerService::class.java
            ).addStamp(id)
            .enqueue(object : Callback<Customer> {
                override fun onResponse(
                    call: Call<Customer>,
                    response: Response<Customer>
                ) {
                    response.body()?.let { customer ->
                        customerLiveData.value = customer
                    }
                }

                override fun onFailure(call: Call<Customer>, t: Throwable) {
                    Log.e("call", call.toString())
                    Log.e("error", t.message!!)
                }

            })
        return customerLiveData
    }
}