package com.giraffe.tudeeapp.di

import androidx.room.Room
import com.giraffe.tudeeapp.data.database.TudeeDatabase
import com.giraffe.tudeeapp.data.util.Constants.DATABASE_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            TudeeDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    single {
        val database = get<TudeeDatabase>()
        database.taskDao()
    }

    single {
        val database = get<TudeeDatabase>()
        database.categoryDao()
    }
}