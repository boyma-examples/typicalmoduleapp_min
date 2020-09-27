package ru.typicalmoduleapp.utils.livedata

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

@Suppress("unused")
inline fun <T> LifecycleOwner.observe(data: LiveData<T>, crossinline observer: (T?) -> Unit) {
    data.observe(this, Observer<T> {
        observer.invoke(it)
    })
}

@Suppress("unused")
inline fun <T> LifecycleOwner.observeNonNull(data: LiveData<T>, crossinline observer: (T) -> Unit) {
    data.observe(this, Observer<T> {
        if (it != null)
            observer.invoke(it)
    })
}

@Suppress("unused")
inline fun <T> Fragment.observeOnView(data: LiveData<T>, crossinline observer: (T?) -> Unit) {
    data.observe(viewLifecycleOwner, Observer<T> {
        observer.invoke(it)
    })
}

@Suppress("unused")
inline fun <T> Fragment.observeNonNullOnView(data: LiveData<T>, crossinline observer: (T) -> Unit) {
    data.observe(viewLifecycleOwner, Observer<T> {
        if (it != null)
            observer.invoke(it)
    })
}
