package com.giraffe.tudeeapp.data.database

import TaskDao
import androidx.room.Database
import androidx.room.RoomDatabase
import com.giraffe.tudeeapp.data.dto.CategoryDto
import com.giraffe.tudeeapp.data.dto.TaskDto


@Database(entities = [TaskDto::class, CategoryDto::class], version = 1)
abstract class TudeeDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao

}