package com.giraffe.tudeeapp.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import com.giraffe.tudeeapp.data.database.TudeeDatabase
import com.giraffe.tudeeapp.data.preferences.DataStorePreferences
import com.giraffe.tudeeapp.data.service.CategoriesService
import com.giraffe.tudeeapp.data.util.DatabaseConstants.DATABASE_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single { DataStorePreferences(androidContext()) }

    single {
        val database = get<TudeeDatabase>()
        database.taskDao()
    }

    single {
        val database = get<TudeeDatabase>()
        database.categoryDao()
    }

    single {
        CategoriesService(
            dao = get(),
            context = androidContext()
        )
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            TudeeDatabase::class.java,
            DATABASE_NAME,
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(connection: SQLiteConnection) {
                super.onCreate(connection)
                CoroutineScope(Dispatchers.IO).launch {
                    get<CategoriesService>().createDefaultCategoriesIfEmpty()
                }
            }
        }).build()
    }
}