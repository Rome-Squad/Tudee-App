package com.giraffe.tudeeapp.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.giraffe.tudeeapp.data.util.Constants.CATEGORY_DATABASE_NAME

abstract class CategoryDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

    companion object {
        private const val DATABASE_NAME = CATEGORY_DATABASE_NAME


        @Volatile
        private var instance: CategoryDatabase? = null


        fun getInstance(context: Context): CategoryDatabase {
            return instance ?: synchronized(this) { buildDatabase(context).also { instance = it } }
        }

        private fun buildDatabase(context: Context): CategoryDatabase {
            return Room.databaseBuilder(context, CategoryDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}