package com.giraffe.tudeeapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.service.TudeeAppService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    val appService: TudeeAppService
) : ViewModel() {
    var isDarkTheme by mutableStateOf(false)
        private set


    private val _statusBarColor = MutableStateFlow(Color.Transparent)
    val statusBarColor = _statusBarColor.asStateFlow()

    init {
        getIsDarkTheme()
    }

    fun getIsDarkTheme() = viewModelScope.launch {
        isDarkTheme = appService.getCurrentTheme()
    }

    fun onToggleTheme() = viewModelScope.launch {
        isDarkTheme = !isDarkTheme
        appService.setCurrentTheme(isDarkTheme)
    }

    fun setStatusBarColor(color: Color) {
        _statusBarColor.update { color }
    }

}