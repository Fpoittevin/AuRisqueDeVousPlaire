package com.poittevin.francois.aurisquedevousplaire.ui.dataBindingUtils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.poittevin.francois.aurisquedevousplaire.utils.Utils

object DateAdapter {

    @JvmStatic
    @BindingAdapter("app:day", "app:month", requireAll = false)
    fun bindDate(view: TextView, day: Int?, month: Int?) {
        if(day != null && month != null) {
            val stringDay = Utils.formatIntToStringWithZero(day)
            val stringMonth = Utils.formatIntToStringWithZero(month)
            val stringDate = "$stringDay/$stringMonth"
            view.text = stringDate
        }
    }
}