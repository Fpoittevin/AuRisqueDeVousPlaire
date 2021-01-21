package com.poittevin.francois.aurisquedevousplaire.ui.dataBindingUtils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.poittevin.francois.aurisquedevousplaire.utils.Utils

object IconAdapter {
    @JvmStatic
    @BindingAdapter(
        "birthdayDay", "birthdayMonth",
        requireAll = false
    )
    fun bindBirthdayIcon(
        view: ImageView,
        birthdayDay: Int?,
        birthdayMonth: Int?
    ) {
        view.visibility = View.INVISIBLE
        if (
            birthdayDay != null
            && birthdayMonth != null
            && Utils.isBirthdayToday(birthdayDay, birthdayMonth)
        ) {
            view.visibility = View.VISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter("reductionNumber", requireAll = false)
    fun bindPromotionIcon(view: ImageView, reductionNumber: Int) {
        view.visibility = View.INVISIBLE
        if (reductionNumber != 0) {
            view.visibility = View.VISIBLE
        }
    }
}