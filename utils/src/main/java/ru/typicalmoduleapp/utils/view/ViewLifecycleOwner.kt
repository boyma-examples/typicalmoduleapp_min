package ru.typicalmoduleapp.utils.view

import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.*


internal class ViewLifecycleOwner(
    baseLifecycleOwner: LifecycleOwner,
    view: View
) : LifecycleOwner {
    private val registry = LifecycleRegistry(this)
    override fun getLifecycle() = registry

    private val baseLifecycle = baseLifecycleOwner.lifecycle
    private var viewAttached: Boolean = ViewCompat.isAttachedToWindow(view)

    private val baseLifecycleObserver = object : LifecycleObserver {
        @Suppress("unused")
        @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
        fun onEvent() {
            updateState()
        }
    }

    private val attachStateListener = object : View.OnAttachStateChangeListener {
        override fun onViewDetachedFromWindow(v: View) {
            viewAttached = false
            baseLifecycle.removeObserver(baseLifecycleObserver)
            updateState()
        }

        override fun onViewAttachedToWindow(v: View) {
            viewAttached = true
            baseLifecycle.addObserver(baseLifecycleObserver)
            updateState()
        }
    }

    init {
        if (viewAttached)
            baseLifecycle.addObserver(baseLifecycleObserver)
        view.addOnAttachStateChangeListener(attachStateListener)
        updateState()
    }

    private fun updateState() {
        val baseState = baseLifecycle.currentState
        // do NOT let lifecycle state be too high if the view is not attached
        if (!viewAttached && baseState.isAtLeast(Lifecycle.State.CREATED)) {
            registry.currentState = Lifecycle.State.CREATED
        } else {
            registry.currentState = baseState
        }
    }
}
