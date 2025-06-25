package com.giraffe.tudeeapp.domain.service

interface AppService {
    suspend fun setDarkThemeStatus(isDark: Boolean)
    suspend fun isDarkTheme(): Boolean
    suspend fun setFirsTimeStatus()
    suspend fun isFirsTime(): Boolean
}