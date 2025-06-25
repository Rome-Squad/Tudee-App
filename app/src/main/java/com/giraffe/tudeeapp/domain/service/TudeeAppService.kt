package com.giraffe.tudeeapp.domain.service

import kotlinx.coroutines.flow.Flow

interface TudeeAppService {
    suspend fun setCurrentTheme(isDark: Boolean?)
    fun getCurrentTheme(): Flow<Boolean?>

    suspend fun setOnboardingShown(shown: Boolean)
    suspend fun isOnboardingShown(): Boolean
}