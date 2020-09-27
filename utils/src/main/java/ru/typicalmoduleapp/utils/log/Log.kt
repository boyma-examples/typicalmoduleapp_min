package ru.typicalmoduleapp.utils.log

import android.util.Log
import ru.typicalmoduleapp.utils.BuildConfig

@Suppress("unused")
object Log {
    private const val LOG_TAG = "typicalmoduleapp"

    val enabled: Boolean
        get() = BuildConfig.LOGGING_ENABLED

    fun v(message: String) {
        if (enabled) Log.v(LOG_TAG, message)
    }

    fun v(message: String, tr: Throwable) {
        if (enabled) Log.v(LOG_TAG, message, tr)
    }

    fun d(message: String) {
        if (enabled) Log.d(LOG_TAG, message)
    }

    fun d(message: String, tr: Throwable) {
        if (enabled) Log.d(LOG_TAG, message, tr)
    }

    fun i(message: String) {
        if (enabled) Log.i(LOG_TAG, message)
    }

    fun i(message: String, tr: Throwable) {
        if (enabled) Log.i(LOG_TAG, message, tr)
    }

    fun w(message: String) {
        if (enabled) Log.w(LOG_TAG, message)
    }

    fun w(message: String, tr: Throwable) {
        if (enabled) Log.w(LOG_TAG, message, tr)
    }

    fun w(tr: Throwable) {
        if (enabled) Log.w(LOG_TAG, tr)
    }

    fun e(message: String) {
        if (enabled) Log.e(LOG_TAG, message)
    }

    fun e(message: String, tr: Throwable) {
        if (enabled) Log.e(LOG_TAG, message, tr)
    }
}
