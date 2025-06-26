package com.giraffe.tudeeapp.presentation.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.service.AppService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel(
    val appService: AppService
) : ViewModel(), AppInteractionListener {

    private val _state = MutableStateFlow(AppState())
    val state = _state.asStateFlow()

    init {
        isDarkTheme()
        isFirsTime()
    }

    override fun isDarkTheme() {
        viewModelScope.launch(Dispatchers.IO) {
            appService.isDarkTheme()
                .collect { isDarkTheme ->
                    _state.update { it.copy(isDarkTheme = isDarkTheme) }
                }
        }
    }

    override fun isFirsTime() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isFirstTime = appService.isFirsTime()) }
        }
    }

    override fun setFirsTimeStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            appService.setFirsTimeStatus()
        }
    }
}