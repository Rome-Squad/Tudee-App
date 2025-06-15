package com.giraffe.tudeeapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.giraffe.tudeeapp.data.Task


@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        private const val DATABASE_NAME = "task_database"

        @Volatile
        private var instance: TaskDatabase? = null

        fun getInstance(context: Context): TaskDatabase {
            return instance ?: synchronized(this) { buildDatabase(context).also { instance = it } }
        }

        fun buildDatabase(context: Context): TaskDatabase {
            return Room.databaseBuilder(context, TaskDatabase::class.java, DATABASE_NAME).build()
        }
    }
}