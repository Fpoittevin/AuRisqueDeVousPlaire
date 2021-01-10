package com.poittevin.francois.aurisquedevousplaire.utils

import org.joda.time.LocalDate

class Utils {

    companion object {
        fun isBirthdayToday(birthdayDay: Int, birthdayMonth: Int): Boolean {
            val today = LocalDate.now()

            return birthdayDay == today.dayOfMonth && birthdayMonth == today.monthOfYear
        }
    }
}