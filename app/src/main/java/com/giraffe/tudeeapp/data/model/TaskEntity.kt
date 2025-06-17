package com.giraffe.tudeeapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.tudeeapp.data.util.Constants
import com.giraffe.tudeeapp.domain.model.task.TaskPriority
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import kotlinx.datetime.LocalDateTime

@Entity(tableName = Constants.TASK_TABLE_NAME)
data class TaskEntity (
    @PrimaryKey val uid: Long,
    val title: String,
    val description: String,
    val taskPriority: TaskPriority,
    val status: TaskStatus,
    val categoryId: Long,
   val dueDate: LocalDateTime,
   val createdAt: LocalDateTime,
   val updatedAt: LocalDateTime
)