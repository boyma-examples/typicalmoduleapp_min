package ru.typicalmoduleapp.utils.bindingadapters

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("android:visibility")
fun bindViewVisibility(view: View, visible: Boolean?) {
    if (visible == null) return
    view.visibility = if (visible == true) View.VISIBLE else View.GONE
}
