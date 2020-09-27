package ru.typicalmoduleapp.utils.livedata

import androidx.lifecycle.MutableLiveData

@Suppress("unused", "nothing_to_inline")
inline fun <T> MutableLiveData<out T>.updateValue(newValue: T?): Boolean {
    if (value != newValue) {
        value = newValue
        return true
    }
    return false
}
