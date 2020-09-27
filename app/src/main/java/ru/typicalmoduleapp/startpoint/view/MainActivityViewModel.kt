package ru.typicalmoduleapp.startpoint.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import ru.typicalmoduleapp.appcore.common.auth.AuthManager
import ru.typicalmoduleapp.utils.livedata.ActionLiveData
import javax.inject.Inject

interface MainActivityViewModel

class MainActivityViewModelImpl @Inject constructor(
    private val authManager: AuthManager
) : ViewModel(), MainActivityViewModel {
    val showBottomNavigationMenu = MutableLiveData(true)
    val authEvents = ActionLiveData<AuthManager.AuthEvent>()

    fun isLoggedIn() = authManager.currentUser != null

    private val subscriptions = CompositeDisposable()

    init {
        authManager.authEvents.subscribe(authEvents::setValue).addTo(subscriptions)
    }

    override fun onCleared() {
        subscriptions.clear()
        super.onCleared()
    }
}
