package com.poittevin.francois.aurisquedevousplaire.ui.dataBindingUtils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.slider.RangeSlider

object RangeSliderAdapter {

    @JvmStatic
    @BindingAdapter("app:values")
    fun setValue(view: RangeSlider, newValue: Array<Int?>) {
        val floatValues = mutableListOf<Float?>(null, null)
        newValue.let {
            if (view.values != newValue) {
                newValue.forEachIndexed { index, value ->
                    when (index) {
                        0 -> {
                            value?.let {
                                floatValues[index] = value.toFloat()
                            } ?: run {
                                floatValues[index] = view.valueFrom
                            }
                        }
                        1 -> {
                            value?.let {
                                floatValues[index] = value.toFloat()
                            } ?: run {
                                floatValues[index] = view.valueTo
                            }
                        }
                    }
                }
            }
        }
        view.values = floatValues
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "app:values")
    fun getValue(view: RangeSlider): Array<Int?> {
        val values = mutableListOf<Int?>(null, null)
        view.values.forEachIndexed { index, value ->
            when (index) {
                0 -> {
                    if (value != view.valueFrom) {
                        values[index] = value.toInt()
                    } else {
                        values[index] = null
                    }
                }
                1 -> {
                    if (value != view.valueTo) {
                        values[index] = value.toInt()
                    } else {
                        values[index] = null
                    }
                }
            }
        }
        return values.toTypedArray()
    }

    @JvmStatic
    @BindingAdapter("app:valuesAttrChanged")
    fun setRangeSliderListeners(slider: RangeSlider, attrChange: InverseBindingListener) {
        slider.addOnChangeListener { _, _, _ ->
            attrChange.onChange()
        }
    }

    @JvmStatic
    @BindingAdapter("app:range_slider_value", "app:default", requireAll = true)
    fun bindRangeSliderValues(
        view: TextView,
        rangeSliderValue: Int?,
        default: Int?
    ) {
        rangeSliderValue?.let {
            view.text = it.toString()
        } ?: run {
            default?.let { default ->
                view.text = default.toString()
            }
        }
    }
}