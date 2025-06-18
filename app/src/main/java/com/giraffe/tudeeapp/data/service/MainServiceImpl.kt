package com.giraffe.tudeeapp.data.service

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.giraffe.tudeeapp.domain.service.MainService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "main_prefs")


class MainServiceImpl(applicationContext: Context): MainService {
    private val context = applicationContext.applicationContext
    private val IS_DARK_THEME_KEY = booleanPreferencesKey("is_dark_theme")
    override suspend fun setCurrentTheme(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_DARK_THEME_KEY] = isDark
        }
    }

    override suspend fun getCurrentTheme(): Boolean {
        return context.dataStore.data
            .map { preferences ->
                preferences[IS_DARK_THEME_KEY] ?: false
            }.first()
    }
}