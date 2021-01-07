package com.poittevin.francois.aurisquedevousplaire.api

import com.poittevin.francois.aurisquedevousplaire.models.Customer
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface CustomerService {

    companion object {

        private const val BASE_URL = "http://192.168.1.27:8888/ardvp/api/app/"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @GET("customer/list")
    fun getCustomersList(): Call<List<Customer>>

    @GET("customer/details/{id}")
    fun getCustomer(@Path("id") id: Int): Call<Customer>

    @GET("customer/addStamp/{id}")
    fun addStamp(@Path("id") id: Int): Call<Customer>
}