package com.poittevin.francois.aurisquedevousplaire.api

import com.poittevin.francois.aurisquedevousplaire.events.FailureEvent
import com.poittevin.francois.aurisquedevousplaire.models.Message
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessageHelper {

    private val apiService = ApiService
        .retrofit
        .create(
            ApiService::class.java
        )

    fun sendMessage(message: Message) {

        apiService.sendMessage(message)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {}

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    EventBus.getDefault()
                        .post(FailureEvent("une erreur s'est produite lors de la synchronisation avec le serveur"))
                }

            })
    }
}