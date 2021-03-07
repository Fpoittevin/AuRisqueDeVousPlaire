package com.poittevin.francois.aurisquedevousplaire.api

import com.poittevin.francois.aurisquedevousplaire.BuildConfig
import com.poittevin.francois.aurisquedevousplaire.models.Customer
import com.poittevin.francois.aurisquedevousplaire.models.Message
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    companion object {

        private const val BASE_URL = "http://146.59.233.12/app/"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Headers("key:" + BuildConfig.API_KEY)
    @GET("customer/list")
    fun getCustomersList(): Call<List<Customer>>

    @Headers("key:" + BuildConfig.API_KEY)
    @POST("customer/update")
    fun updateCustomer(@Body customer: Customer): Call<Customer>

    @Headers("key:" + BuildConfig.API_KEY)
    @POST("customer/insert")
    fun insertCustomer(@Body customer: Customer): Call<Customer>

    @Headers("key:" + BuildConfig.API_KEY)
    @POST("customer/delete")
    fun deleteCustomer(@Body customer: Customer): Call<Void>

    @Headers("key:" + BuildConfig.API_KEY)
    @POST("message/send")
    fun sendMessage(@Body message: Message): Call<Void>
}