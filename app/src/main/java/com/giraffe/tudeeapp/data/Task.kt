package com.giraffe.tudeeapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.tudeeapp.domain.model.task.TaskPriority
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import kotlinx.datetime.LocalDateTime

@Entity
data class Task (
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