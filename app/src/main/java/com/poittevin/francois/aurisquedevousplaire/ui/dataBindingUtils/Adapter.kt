package com.poittevin.francois.aurisquedevousplaire.ui.dataBindingUtils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.poittevin.francois.aurisquedevousplaire.utils.Utils

object IconAdapter {
    @JvmStatic
    @BindingAdapter("birthdayDay", "birthdayMonth",
        requireAll = false)
    fun bindBirthdayIcon(view: ImageView,
                         birthdayDay: Int?,
                         birthdayMonth: Int?
    ) {
        if (birthdayDay != null && birthdayMonth != null) {
            if (Utils.isBirthdayToday(birthdayDay, birthdayMonth)) {
                view.visibility = View.VISIBLE
            } else {
                view.visibility = View.INVISIBLE
            }
        }
    }

    @JvmStatic
    @BindingAdapter("haveReduction", requireAll = false)
    fun bindPromotionIcon(view: ImageView, haveReduction: Boolean?) {
        if(haveReduction != null && haveReduction) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.INVISIBLE
        }
    }
}