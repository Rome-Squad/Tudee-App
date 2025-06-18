package com.giraffe.tudeeapp.presentation.shared

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.service.MainService
import kotlinx.coroutines.launch

class MainViewModel(
    val mainService: MainService
): ViewModel() {
    var isDarkTheme by mutableStateOf(false)
    private set

    init {
        getIsDarkTheme()
    }

    fun getIsDarkTheme() = viewModelScope.launch {
        isDarkTheme = mainService.getCurrentTheme()
    }

    fun onToggleTheme() = viewModelScope.launch {
        isDarkTheme = !isDarkTheme
        mainService.setCurrentTheme(isDarkTheme)
    }

}