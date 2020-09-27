package ru.typicalmoduleapp.utils.view

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.R as MR

fun Fragment.showSnackbar(
    text: CharSequence,
    duration: Int? = null,
    view: View? = null,
    handler: (Snackbar.() -> Unit)? = null
) {
    val v = view ?: activity?.findViewById<ViewGroup>(android.R.id.content)?.getChildAt(0) ?: return
    Snackbar.make(v, text, calcDuration(text, duration)).apply {
        this.view.findViewById<TextView>(MR.id.snackbar_text).maxLines = Int.MAX_VALUE
        handler?.invoke(this)
    }.show()
}

private fun calcDuration(text: CharSequence, duration: Int?) =
    duration ?: if (text.length > 50) 10000 else Snackbar.LENGTH_LONG
