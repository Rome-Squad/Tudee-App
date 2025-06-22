package com.giraffe.tudeeapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.service.TudeeAppService
import kotlinx.coroutines.launch

class MainViewModel(
    val appService: TudeeAppService
): ViewModel() {
    var isDarkTheme by mutableStateOf(false)
    private set

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

}