package com.giraffe.tudeeapp.data.service

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.giraffe.tudeeapp.domain.service.SplashService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "splash_prefs")

class SplashServiceImpl( private val applicationContext: Context) : SplashService {

    private val ONBOARDING_SHOWN_KEY = booleanPreferencesKey("onboarding_shown")

    override suspend fun setOnboardingShown(shown: Boolean) {
        applicationContext.dataStore.edit { preferences ->
            preferences[ONBOARDING_SHOWN_KEY] = shown
        }
    }

    override suspend fun isOnboardingShown(): Boolean {
        return applicationContext.dataStore.data
            .map { preferences ->
                preferences[ONBOARDING_SHOWN_KEY] ?: false
            }.first()
    }
}