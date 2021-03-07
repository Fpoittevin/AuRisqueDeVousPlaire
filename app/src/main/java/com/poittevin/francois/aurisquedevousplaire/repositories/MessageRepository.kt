package com.poittevin.francois.aurisquedevousplaire.repositories

import com.poittevin.francois.aurisquedevousplaire.api.MessageHelper
import com.poittevin.francois.aurisquedevousplaire.models.Message

class MessageRepository {

    private val messageHelper = MessageHelper()

    suspend fun sendMessage(message: Message) {
        messageHelper.sendMessage(message)
    }
}