package ru.typicalmoduleapp.utils.bindingadapters

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("html")
fun bindTextViewHtml(view: TextView, htmlText: String?) {
    view.setText(
        spannedFromHtml(htmlText ?: ""),
        TextView.BufferType.SPANNABLE
    )
    view.movementMethod = LinkMovementMethod()
}

private fun spannedFromHtml(html: String): Spanned =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(html)
    }
