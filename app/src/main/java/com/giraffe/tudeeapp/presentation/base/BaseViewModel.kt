package com.giraffe.tudeeapp.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

abstract class BaseViewModel<S, E>(initialState: S): ViewModel(), KoinComponent {
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _effect = Channel<E>()
    val effect = _effect.receiveAsFlow()

    private val _snackBar = MutableStateFlow<String?>(null)
    val snackBar = _snackBar.asStateFlow()

    protected fun <T> safeExecute(
        onError: (Throwable) -> Unit = {},
        onSuccess: (T) -> Unit = {},
        coroutineScope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        block: suspend () -> T
    ) {
        coroutineScope.launch(dispatcher) {
            try {
                onSuccess(block())
            } catch (e: Throwable) {
                onError(e)
            }
        }
    }

    protected fun <T> safeCollect(
        onError: (Throwable) -> Unit = {},
        onEmitNewValue: (T) -> Unit = {},
        coroutineScope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        block: suspend () -> Flow<T>
    ) {
        coroutineScope.launch(dispatcher) {
            val flow = block()
            flow
                .catch {
                    onError(it)
                }.collect {
                    onEmitNewValue(it)
                }
        }
    }

    protected fun updateState(updater: (S) -> S) {
        _state.update(updater)
    }

    protected fun sendEffect(
        effect: E,
        coroutineScope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.Main,
    ) {
        coroutineScope.launch(dispatcher) {
            _effect.send(effect)
        }
    }

    private var snackBarJob: Job? = null
    protected fun showSnackBar(message: String) {
        snackBarJob?.cancel()
        snackBarJob = viewModelScope.launch {
            _snackBar.emit(message)
            delay(SNACK_BAR_DURATION)
            _snackBar.emit(null)
        }
    }

    companion object {
        const val SNACK_BAR_DURATION = 3000L
    }

}