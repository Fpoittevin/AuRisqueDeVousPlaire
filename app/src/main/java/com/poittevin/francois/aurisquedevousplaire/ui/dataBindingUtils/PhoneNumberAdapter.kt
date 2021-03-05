package com.poittevin.francois.aurisquedevousplaire.ui.dataBindingUtils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.poittevin.francois.aurisquedevousplaire.utils.Utils

object PhoneNumberAdapter {

    @JvmStatic
    @BindingAdapter("app:phoneNumber", requireAll = false)
    fun bindPhoneNumber(view: TextView, phoneNumber: String?) {
        phoneNumber?.let {
            view.text = Utils.formatPhoneNumber(it)
        }
    }
}