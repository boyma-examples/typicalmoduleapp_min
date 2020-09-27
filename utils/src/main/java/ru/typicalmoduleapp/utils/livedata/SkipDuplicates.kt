package ru.typicalmoduleapp.utils.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

@Suppress("unused", "nothing_to_inline")
inline fun <S> LiveData<S>.skipDuplicates() =
    MediatorLiveData<S>().also { r ->
        r.addSource(this) { s ->
            if (s != r.value)
                r.value = s
        }
    }.also { it.value = this.value }
