package com.poittevin.francois.aurisquedevousplaire.ui.dataBindingUtils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.poittevin.francois.aurisquedevousplaire.utils.Utils

object DateAdapter {

    @JvmStatic
    @BindingAdapter("app:timestamp", requireAll = false)
    fun bindDate(view: TextView, timestamp: Long?) {
        timestamp?.let {
            val date = Utils.convertTimestampToStringDate(it)
            date?.let { dateVal ->
                view.text = dateVal
            }
        }
    }
}