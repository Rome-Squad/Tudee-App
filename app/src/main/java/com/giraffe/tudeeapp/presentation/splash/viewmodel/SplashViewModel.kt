package com.giraffe.tudeeapp.presentation.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.service.TudeeAppService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val appService: TudeeAppService
) : ViewModel() {

    private val _isOnboardingShown = MutableStateFlow<Boolean?>(null)
    val isOnboardingShown: StateFlow<Boolean?> = _isOnboardingShown

    fun checkOnboardingStatus() {
        viewModelScope.launch {
            _isOnboardingShown.value = appService.isOnboardingShown()
        }
    }

    fun setOnboardingShown(shown: Boolean) {
        viewModelScope.launch {
            appService.setOnboardingShown(shown)
        }
    }
}