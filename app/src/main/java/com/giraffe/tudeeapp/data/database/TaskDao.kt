package com.giraffe.tudeeapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.giraffe.tudeeapp.data.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAll(): List<Task>

    @Query("SELECT * FROM task WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Task>

//    @Query("SELECT * FROM task WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User

    @Insert
    fun insertAll(vararg users: Task)

    @Delete
    fun delete(task: Task)
}