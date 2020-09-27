package ru.typicalmoduleapp.utils.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.*

@Suppress("unused")
class ReplayLiveData<T> : MutableLiveData<T>() {

    private val queue = LinkedList<T>() as Queue<T>
    private var currentObserver: ObserverWrapper? = null

    override fun setValue(value: T?) {
        if (value != null && hasObservers())
            queue.offer(value)

        super.setValue(null)
    }

    override fun postValue(value: T?) {
        if (value != null && hasObservers())
            queue.offer(value)

        super.postValue(null)
    }

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
        if (!hasObservers()) {
            currentObserver = null
            queue.clear()
        }
    }

    @MainThread
    override fun removeObservers(owner: LifecycleOwner) {
        super.removeObservers(owner)

        if (!hasObservers()) {
            currentObserver = null
            queue.clear()
        }
    }

    private inner class ObserverWrapper(val observer: Observer<in T?>) : Observer<T?> {
        private var dispatching = false

        override fun onChanged(data: T?) {
            if (dispatching)
                return

            val queueCopy = LinkedList<T>(queue)
            queue.clear()

            // Send all the events to the observer
            dispatching = true
            queueCopy.forEach { observer.onChanged(it) }
            dispatching = false

            // In case observer added some values to queue while handling onChanged, post
            // null value in order to schedule handling of the new portion of data
            if (queue.isNotEmpty())
                super@ReplayLiveData.postValue(null)
        }
    }
}
