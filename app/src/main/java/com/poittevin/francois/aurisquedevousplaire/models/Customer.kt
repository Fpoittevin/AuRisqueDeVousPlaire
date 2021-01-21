package com.poittevin.francois.aurisquedevousplaire.models

import com.google.gson.annotations.SerializedName

data class Customer(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("firstName")
    var firstName: String? = null,
    @SerializedName("lastName")
    var lastName: String? = null,
    @SerializedName("birthdayDay")
    var birthdayDay: Int? = null,
    @SerializedName("birthdayMonth")
    var birthdayMonth: Int? = null,
    @SerializedName("phoneNumber")
    var phoneNumber: String? = null,
    @SerializedName("emailAddress")
    var emailAddress: String? = null,
    @SerializedName("stampsNumber")
    var stampsNumber: Int = 0,
    @SerializedName("cardsNumber")
    var cardsNumber: Int = 1,
    @SerializedName("reductionNumber")
    var reductionNumber: Int = 0,
    @SerializedName("comment")
    var comment: String? = null
)