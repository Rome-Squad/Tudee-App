package com.giraffe.tudeeapp.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


class DataStorePreferences(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "tudee_data_store"
    )

    suspend fun setFirstTimeStatus() {
        context.dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(
                IS_FIRST_TIME
            )] = false
        }
    }

    suspend fun isFirstTime() = context.dataStore.data.map { preferences ->
        preferences[booleanPreferencesKey(IS_FIRST_TIME)] != false
    }.first()

    suspend fun setDarkThemeStatus(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(
                IS_DARK_THEME
            )] = isDark
        }
    }

    suspend fun isDarkTheme() = context.dataStore.data.map { preferences ->
        preferences[booleanPreferencesKey(IS_DARK_THEME)] != false
    }.first()

    companion object {
        const val IS_FIRST_TIME = "IS_FIRST_TIME"
        const val IS_DARK_THEME = "IS_DARK_THEME"
    }

}