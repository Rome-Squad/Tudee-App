package com.giraffe.tudeeapp.data.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.tudeeDataStore: DataStore<Preferences> by preferencesDataStore(name = "app_prefs")
