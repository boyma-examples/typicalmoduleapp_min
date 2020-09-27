package com.typicalmoduleapp.postfeature.posts.view

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.typicalmoduleapp.postfeature.posts.domain.PostsInteractor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import ru.typicalmoduleapp.appcore.common.net.errorhandle.ErrorParser
import ru.typicalmoduleapp.appcore.common.net.errorhandle.ErrorType
import ru.typicalmoduleapp.utils.livedata.ActionLiveData
import ru.typicalmoduleapp.utils.log.Log
import javax.inject.Inject

interface PostsFragmentViewModel

class PostsFragmentViewModelImpl @Inject constructor(
    private val postsInteractor: PostsInteractor,
    private val errorParser: ErrorParser
) : ViewModel(), PostsFragmentViewModel, LifecycleObserver {

    /**Navigate***************************/
    sealed class Navigate {
        class ToSelf(val title: String) : Navigate()
    }

    val navigate = ActionLiveData<Navigate>()
    /*****************************/

    /**Command***************************/
    sealed class Command {
        object ClearCodeInputText : Command()
        object FocusCodeInput : Command()
    }

    val command = ActionLiveData<Command>()
    /*****************************/

    /**State***************************/
    sealed class State {
        object First : State()
        object Normal : State()
        object Loading : State()
    }

    private val state = MutableLiveData<State>().apply { value = State.First }

    /*****************************/

    ////////////!uidata
    /*val codeInput = mutableLiveDataOf("").also { f ->
        f.observeForever{onCodeInputTextChanged(f.value ?: "")}
    }

    val codeInputEnabled = createMediatorLiveData(state) { it is State.Normal }
    val showCodeInput = createMediatorLiveData(state) { it !is State.Loading }
    val showLoading = createMediatorLiveData(state) { it is State.Loading }
    val showCenterProgress = createMediatorLiveData(state) { it is State.First }
    val showMainLayout = createMediatorLiveData(state) { it is State.Normal || it is State.Loading }
    /////
    ////////////!data
    private lateinit var confirmArgs: PasswordRecoveryConfirmArgs
    private lateinit var requestSmsResponse: ConfirmRecoverySmsResponse*/
    /////
    ////////////!subscription
    private val subs = CompositeDisposable()

    /////
    ////////////!onCleared
    override fun onCleared() {
        super.onCleared()
        cancelConnections()
    }

    private fun cancelConnections() {
        subs.clear()
    }
    /////

    fun on() {
        postsInteractor.getContacts().subscribeBy(
            onSuccess = {

            },
            onError = {
                it.printStackTrace()
                Log.e(errorParser.getErrorMessage(it))
                val errModel = errorParser.getErrorModel(it)
                if (errModel.localErrorType == ErrorType.TOAST) {
                    Log.e("Showing Toast with msg:" + errModel.message)
                } else {
                    Log.e("Showing Not Toast with msg:" + errModel.message)
                }
            })
            .addTo(subs)
    }

    fun onButtonClick() {
        navigate.value = Navigate.ToSelf("")
    }
}
