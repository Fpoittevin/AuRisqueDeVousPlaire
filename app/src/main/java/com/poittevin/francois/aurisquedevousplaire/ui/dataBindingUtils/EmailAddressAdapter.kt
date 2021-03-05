package com.poittevin.francois.aurisquedevousplaire.ui.dataBindingUtils

import android.widget.TextView
import androidx.databinding.BindingAdapter

object EmailAddressAdapter {

    @JvmStatic
    @BindingAdapter("app:emailAddress", requireAll = false)
    fun bindEmailAddress(view: TextView, emailAddress: String?) {
        emailAddress?.let {
            view.text = it
        }
    }
}