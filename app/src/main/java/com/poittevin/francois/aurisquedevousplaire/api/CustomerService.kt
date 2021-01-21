package com.poittevin.francois.aurisquedevousplaire.api

import com.poittevin.francois.aurisquedevousplaire.models.Customer
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CustomerService {

    companion object {

        private const val BASE_URL = "http://192.168.1.26:8888/ardvp/api/app/"

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

    @GET("customer/useReduction/{id}")
    fun useReduction(@Path("id") id: Int): Call<Customer>

    @POST("customer/update")
    fun updateCustomer(@Body customer: Customer): Call<Customer>

    @POST("customer/insert")
    fun insertCustomer(@Body customer: Customer): Call<Customer>

    @POST("customer/search")
    fun searchCustomers(@Body searchString: Map<String, String>): Call<List<Customer>>

    @GET("customer/getMinMaxNumberOfCards")
    fun getMinMaxNumberOfCards(): Call<Array<Int>>

    @POST("customer/numberOfCustomersInRangeCardsNumber")
    fun getNumberOfCustomersInRangeCardsNumber(@Body minMax: Map<String, Int>): Call<Int>
}