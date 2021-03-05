package com.poittevin.francois.aurisquedevousplaire.database

import androidx.room.TypeConverter
import com.poittevin.francois.aurisquedevousplaire.utils.ContactChoice

class Converters {
    @TypeConverter
    fun fromInt(value: Int): ContactChoice {
        return when (value) {
            1 -> ContactChoice.EMAIL
            2 -> ContactChoice.SMS
            else -> ContactChoice.NOTHING
        }
    }

    @TypeConverter
    fun contactChoiceToInt(contactChoice: ContactChoice): Int {
        return when (contactChoice) {
            ContactChoice.EMAIL -> 1
            ContactChoice.SMS -> 2
            else -> 0
        }
    }
}