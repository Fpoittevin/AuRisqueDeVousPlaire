package com.poittevin.francois.aurisquedevousplaire.ui.dataBindingUtils

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.slider.RangeSlider

object RangeSliderAdapter {

    @JvmStatic
    @InverseBindingAdapter(attribute = "app:values")
    fun getValue(view: RangeSlider): Array<Int?> {
        val values = mutableListOf<Int?>(null, null)
        view.values.forEachIndexed { index, value ->
            values[index] = value.toInt()
        }
        return values.toTypedArray()
    }

    @JvmStatic
    @BindingAdapter("app:values")
    fun setValue(view: RangeSlider, newValue: Array<Int?>) {
        val floatValues = mutableListOf<Float?>(null, null)

            if (view.values != newValue) {
                newValue.forEachIndexed { index, value ->
                    if (value != null) {
                        floatValues[index] = value.toFloat()
                    }
                }
            }

        view.values = floatValues
    }

    @JvmStatic
    @BindingAdapter("app:valuesAttrChanged")
    fun setRangeSliderListeners(slider: RangeSlider, attrChange: InverseBindingListener) {
        slider.addOnChangeListener { _, _, _ ->
            attrChange.onChange()
        }
    }
}