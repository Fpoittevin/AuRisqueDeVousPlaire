package com.poittevin.francois.aurisquedevousplaire.utils

import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import java.util.*

class Utils {

    companion object {
        fun isBirthdayToday(timestamp: Long): Boolean {
            val today = LocalDate.now()
            val birthday = LocalDate(timestamp)

            return today.dayOfMonth == birthday.dayOfMonth && today.monthOfYear == birthday.monthOfYear
        }

        fun convertTimestampToStringDate(timestamp: Long): String? {
            if (timestamp != 0.toLong()) {
                val date = LocalDate(timestamp)
                val dateFormatter = DateTimeFormat.forPattern("dd/MM/YYYY")

                return dateFormatter.print(date)
            }
            return null
        }

        fun formatPhoneNumber(phoneNumber: String): String {

            val charArray = phoneNumber.toCharArray()
            val stringBuilder = StringBuilder()

            for ((index, char) in charArray.withIndex()) {
                stringBuilder.append(char)

                if (!isNumberEven(index) && index != charArray.size - 1) {
                    stringBuilder.append(".")
                }
            }

            return stringBuilder.toString()
        }

        private fun isNumberEven(number: Int): Boolean = number % 2 == 0

        fun calculateReduction(price: Double): Double {
            val reduction = price.times(15).div(100)
            return price.minus(reduction)
        }

        fun getTimestampFromDatePicker(year: Int, month: Int, day: Int): Long {
            val date: Calendar = Calendar.getInstance()
            date[Calendar.DAY_OF_MONTH] = day
            date[Calendar.MONTH] = month
            date[Calendar.YEAR] = year
            date.set(Calendar.HOUR_OF_DAY, 23)
            date.set(Calendar.MINUTE, 59)
            date.set(Calendar.SECOND, 59)
            return date.timeInMillis
        }
    }
}