package com.poittevin.francois.aurisquedevousplaire.ui.dataBindingUtils

import androidx.databinding.InverseMethod
import com.poittevin.francois.aurisquedevousplaire.utils.Utils

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
            return Utils.formatIntToStringWithZero(it)
        } ?: run {
            return ""
        }
    }
}
