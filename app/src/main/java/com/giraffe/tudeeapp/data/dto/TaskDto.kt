package com.giraffe.tudeeapp.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.tudeeapp.data.util.Constants
import com.giraffe.tudeeapp.domain.entity.task.TaskPriority
import com.giraffe.tudeeapp.domain.entity.task.TaskStatus

@Entity(tableName = Constants.TASK_TABLE_NAME)
data class TaskDto (
    @PrimaryKey(autoGenerate = true) val uid: Long = 0L,
    val title: String,
    val description: String,
    val taskPriority: TaskPriority,
    val status: TaskStatus,
    val categoryId: Long, // foreign key to categories entity
    val dueDate: String,
    val createdAt: String,
    val updatedAt: String
)