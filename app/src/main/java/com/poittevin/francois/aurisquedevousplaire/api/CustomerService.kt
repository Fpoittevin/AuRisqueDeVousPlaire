package com.poittevin.francois.aurisquedevousplaire.api

import com.poittevin.francois.aurisquedevousplaire.models.Customer
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface CustomerService {

    companion object {

        // TODO: add ssl connection

        private const val BASE_URL = "http://146.59.233.12/app/"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @GET("customer/list")
    fun getCustomersList(): Call<List<Customer>>

    @POST("customer/update")
    fun updateCustomer(@Body customer: Customer): Call<Customer>

    @Headers("Content-Type: application/json")
    @POST("customer/insert")
    fun insertCustomer(@Body customer: Customer): Call<Customer>

    @POST("customer/delete")
    fun deleteCustomer(@Body customer: Customer): Call<Void>
}