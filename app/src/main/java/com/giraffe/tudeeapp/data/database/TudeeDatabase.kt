package com.giraffe.tudeeapp.data.database

import TaskDao
import androidx.room.Database
import androidx.room.RoomDatabase
import com.giraffe.tudeeapp.data.model.CategoryEntity
import com.giraffe.tudeeapp.data.model.TaskEntity


@Database(entities = [TaskEntity::class, CategoryEntity::class], version = 1)
abstract class TudeeDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao

}