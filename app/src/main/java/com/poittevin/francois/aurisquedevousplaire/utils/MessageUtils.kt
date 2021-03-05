package com.poittevin.francois.aurisquedevousplaire.utils

class MessageUtils {

    companion object {
        private const val MAX_SMS_CHAR = 149

        fun getNumberOfSmsWithNumberOfChar(numberOfChar: Int): Int {
            return numberOfChar.div(MAX_SMS_CHAR).plus(1)
        }
    }
}