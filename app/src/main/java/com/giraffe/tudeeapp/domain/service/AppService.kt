package com.giraffe.tudeeapp.domain.service

import kotlinx.coroutines.flow.Flow

interface AppService {
    suspend fun setDarkThemeStatus(isDark: Boolean)
    suspend fun isDarkTheme(): Flow<Boolean>
    suspend fun setFirsTimeStatus()
    suspend fun isFirsTime(): Boolean
}