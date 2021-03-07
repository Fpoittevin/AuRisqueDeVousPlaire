package com.poittevin.francois.aurisquedevousplaire.models

data class Message(
    var content: String = "",
    var withReduction: Boolean? = false,
    var minNumberOfCards: Int? = null,
    var maxNumberOfCards: Int? = null
)