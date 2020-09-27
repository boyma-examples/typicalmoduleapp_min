package ru.typicalmoduleapp.utils.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

// based on https://android.jlelse.eu/android-arch-handling-clicks-and-single-actions-in-your-view-model-with-livedata-ab93d54bc9dc
@Suppress("unused")
class ActionLiveData<T> : MutableLiveData<T>() {

    private var currentObserver: ObserverWrapper? = null

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T?>) {
        currentObserver?.let { super.removeObserver(it) }
        super.observe(owner, ObserverWrapper(observer).also { currentObserver = it })
    }

    @MainThread
    override fun observeForever(observer: Observer<in T?>) {
        currentObserver?.let { super.removeObserver(it) }
        super.observeForever(ObserverWrapper(observer).also { currentObserver = it })
    }

    @MainThread
    override fun removeObserver(observer: Observer<in T>) {
        currentObserver?.let {
            if (it.observer === observer)
                super.removeObserver(it)
        }
        if (!hasObservers())
            currentObserver = null
    }

    @MainThread
    override fun removeObservers(owner: LifecycleOwner) {
        super.removeObservers(owner)

        if (!hasObservers())
            currentObserver = null
    }

    private inner class ObserverWrapper(val observer: Observer<in T?>) : Observer<T?> {
        override fun onChanged(data: T?) {
            // We ignore any null values
            if (data != null) {
                observer.onChanged(data)
                // We set the value to null straight after emitting the change to the observer
                value = null
                // This means that the state of the data will always be null / non existent
                // It will only be available to the observer in its callback and since we do not emit null values
                // the observer never receives a null value and any observers resuming do not receive the last event.
                // Therefore it only emits to the observer the single action so you are free to show messages over and over again
                // Or launch an activity/dialog or anything that should only happen once per action / click :).
            }
        }
    }
}


inline infix fun <reified T> ActionLiveData<T>.send(value: T) {
    this.value = value
}

fun ActionLiveData<Unit>.send() {
    this.value = Unit
}

fun MutableLiveData<Unit>.send() {
    this.value = Unit
}