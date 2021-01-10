package com.poittevin.francois.aurisquedevousplaire.ui.dataBindingUtils

import androidx.databinding.InverseMethod

object Converter {
    @InverseMethod("intToStringDate")
    @JvmStatic
    fun stringDateToIntOrNull(value: String): Int? {
        return if (value.isNotEmpty()) {
            value.toInt()
        } else {
            null
        }
    }

    @JvmStatic
    fun intToStringDate(value: Int?): String {
        value?.let {
            return if(value < 10) {
                "0$value"
            } else {
                value.toString()
            }
        } ?: run {
            return ""
        }
    }
}