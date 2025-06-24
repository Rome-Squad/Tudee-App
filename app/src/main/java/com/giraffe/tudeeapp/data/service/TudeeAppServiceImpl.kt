package com.giraffe.tudeeapp.data.service

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.giraffe.tudeeapp.domain.service.TudeeAppService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TudeeAppServiceImpl(private val dataStore: DataStore<Preferences>) : TudeeAppService {
    override suspend fun setCurrentTheme(isDark: Boolean?) {
        dataStore.edit { preferences ->
            if (isDark == null) preferences.remove(IS_DARK_THEME_KEY)
            else
                preferences[IS_DARK_THEME_KEY] = isDark
        }
    }

    override fun getCurrentTheme(): Flow<Boolean?> = dataStore.data
        .map { preferences ->
            preferences[IS_DARK_THEME_KEY]
        }


    override suspend fun setOnboardingShown(shown: Boolean) {
        dataStore.edit { preferences ->
            preferences[ONBOARDING_SHOWN_KEY] = shown
        }
    }

    override suspend fun isOnboardingShown(): Boolean {
        return dataStore.data
            .map { preferences ->
                preferences[ONBOARDING_SHOWN_KEY] == true
            }.first()
    }

    companion object {
        val IS_DARK_THEME_KEY = booleanPreferencesKey("is_dark_theme")
        val ONBOARDING_SHOWN_KEY = booleanPreferencesKey("onboarding_shown")
    }

}