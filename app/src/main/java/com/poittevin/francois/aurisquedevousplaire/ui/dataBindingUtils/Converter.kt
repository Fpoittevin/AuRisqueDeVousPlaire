package com.poittevin.francois.aurisquedevousplaire.ui.dataBindingUtils

import androidx.databinding.InverseMethod

object Converter {

    @InverseMethod("intToString")
    @JvmStatic
    fun stringToInt(value: String): Int {
        return if (value.isNotEmpty()) {
            value.toInt()
        } else {
            0
        }
    }

    @JvmStatic
    fun intToString(value: Int): String {
        return if (value != 0) {
            value.toString()
        } else {
            ""
        }
    }
}