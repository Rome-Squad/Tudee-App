package com.giraffe.tudeeapp.di

import androidx.room.Room
import com.giraffe.tudeeapp.data.database.TudeeDatabase
import com.giraffe.tudeeapp.data.preferences.DataStorePreferences
import com.giraffe.tudeeapp.data.util.DatabaseConstants.DATABASE_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single { DataStorePreferences(androidContext()) }

    single { get<TudeeDatabase>().taskDao() }

    single { get<TudeeDatabase>().categoryDao() }

    single {
        Room.databaseBuilder(androidContext(), TudeeDatabase::class.java, DATABASE_NAME).build()
    }
}