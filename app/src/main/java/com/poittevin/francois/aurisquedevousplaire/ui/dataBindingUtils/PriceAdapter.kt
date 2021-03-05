package com.poittevin.francois.aurisquedevousplaire.ui.dataBindingUtils

import android.widget.TextView
import androidx.databinding.BindingAdapter

object PriceAdapter {

    @BindingAdapter("app:price")
    @JvmStatic
    fun bindPriceWithCurrency(view: TextView, price: Double) {

        val stringPriceFormatted = String.format("%.2f", price)
        val stringPriceFormattedWithCurrency = "$stringPriceFormatted €"

        if (view.text.toString() != stringPriceFormattedWithCurrency) {
            view.text = stringPriceFormattedWithCurrency
        }
    }
}