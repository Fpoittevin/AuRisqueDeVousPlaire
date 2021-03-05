package com.poittevin.francois.aurisquedevousplaire.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.poittevin.francois.aurisquedevousplaire.utils.ContactChoice

@Entity
data class Customer(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("firstName")
    var firstName: String? = null,
    @SerializedName("lastName")
    var lastName: String? = null,
    @SerializedName("birthdayTimestamp")
    var birthdayTimestamp: Long? = null,
    @SerializedName("phoneNumber")
    var phoneNumber: String? = null,
    @SerializedName("emailAddress")
    var emailAddress: String? = null,
    @SerializedName("contactChoice")
    var contactChoice: ContactChoice = ContactChoice.NOTHING,
    @SerializedName("stampsNumber")
    var stampsNumber: Int = 0,
    @SerializedName("cardsNumber")
    var cardsNumber: Int = 1,
    @SerializedName("reductionNumber")
    var reductionNumber: Int = 0,
    @SerializedName("comment")
    var comment: String? = null
)