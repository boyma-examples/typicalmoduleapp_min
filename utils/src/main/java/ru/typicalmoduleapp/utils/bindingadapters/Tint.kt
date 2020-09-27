package ru.typicalmoduleapp.utils.bindingadapters

import android.content.res.ColorStateList
import android.view.View
import androidx.core.view.ViewCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("backgroundTint")
fun setBackgroundTint(view: View, color: Int?) {
    color?.let {
        ViewCompat.setBackgroundTintList(view, ColorStateList.valueOf(it))
    }
}