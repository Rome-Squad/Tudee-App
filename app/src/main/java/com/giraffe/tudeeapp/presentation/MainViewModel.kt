package com.giraffe.tudeeapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.service.TudeeAppService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    val appService: TudeeAppService
) : ViewModel() {
    private val _systemTheme = MutableStateFlow<Boolean?>(null)

    val isDarkTheme = appService.getCurrentTheme()
        .combine(_systemTheme) { userOverridenTheme, systemTheme ->
            userOverridenTheme ?: systemTheme
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = false
        )

    fun setSystemTheme(isDark: Boolean) {
        viewModelScope.launch {
            if (_systemTheme.value == null || _systemTheme.value != isDark) {
                appService.setCurrentTheme(isDark)
            }
            _systemTheme.value = isDark
        }
    }
}