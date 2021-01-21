package com.poittevin.francois.aurisquedevousplaire.utils

import org.joda.time.LocalDate

class Utils {

    companion object {
        fun isBirthdayToday(birthdayDay: Int, birthdayMonth: Int): Boolean {
            val today = LocalDate.now()

            return birthdayDay == today.dayOfMonth && birthdayMonth == today.monthOfYear
        }

        fun formatIntToStringWithZero(value: Int): String {
            return if (value < 10) {
                "0$value"
            } else {
                value.toString()
            }
        }

        fun isNumberEven(number: Int): Boolean = number % 2 == 0

        fun calculateReduction(price: Float): Float {
            val reduction = price.times(15).div(100)
            return price.minus(reduction)
        }
    }
}