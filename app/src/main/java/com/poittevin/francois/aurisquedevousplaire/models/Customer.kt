package com.poittevin.francois.aurisquedevousplaire.models

data class Customer(
    var id: Int,
    var firstName: String,
    var lastName: String,
    var birthdayDay: Int,
    var birthdayMonth: Int,
    var phoneNumber: String,
    var emailAddress: String,
    var stampsNumber: Int,
    var cardsNumber: Int,
    var haveReduction: Int,
    var comment: String
)