package com.giraffe.tudeeapp.data.service

import com.giraffe.tudeeapp.data.preferences.DataStorePreferences
import com.giraffe.tudeeapp.domain.service.AppService

class AppServiceImpl(private val dataStore: DataStorePreferences) : AppService {
    override suspend fun setDarkThemeStatus(isDark: Boolean) = dataStore.setDarkThemeStatus(isDark)
    override suspend fun isDarkTheme() = dataStore.isDarkTheme()
    override suspend fun setFirsTimeStatus() = dataStore.setFirstTimeStatus()
    override suspend fun isFirsTime() = dataStore.isFirstTime()
}