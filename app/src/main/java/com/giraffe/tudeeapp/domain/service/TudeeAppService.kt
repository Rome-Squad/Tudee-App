package com.giraffe.tudeeapp.domain.service

interface TudeeAppService {
    suspend fun setCurrentTheme(isDark: Boolean)
    suspend fun getCurrentTheme(): Boolean

    suspend fun setOnboardingShown(shown: Boolean)
    suspend fun isOnboardingShown(): Boolean
}