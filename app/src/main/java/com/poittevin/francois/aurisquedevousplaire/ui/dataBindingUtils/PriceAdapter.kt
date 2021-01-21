package com.poittevin.francois.aurisquedevousplaire.ui.dataBindingUtils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import com.google.android.material.textfield.TextInputEditText

object PriceAdapter {

    @BindingAdapter("android:text")
    @JvmStatic
    fun setValue(view: TextInputEditText, newValue: Float?) {
        if (view.text.toString() != newValue.toString()) {
            if (newValue != null && !newValue.equals(0F)) {
                view.setText(newValue.toString())
            } else {
                view.setText("")
            }
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "android:text")
    fun getValue(view: TextInputEditText): Float {
        return view.text.toString().toFloat()
    }

    @BindingAdapter("android:text")
    @JvmStatic
    fun setValue(view: TextView, newValue: Float) {
        if (view.text.toString() != newValue.toString()) {
            val textValue = "$newValue €"
            view.text = textValue
        }
    }
}