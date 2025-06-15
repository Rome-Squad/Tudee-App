package com.giraffe.tudeeapp.domain.model.task

import kotlinx.datetime.LocalDateTime

data class Task(
    val id: Long,
    val title: String,
    val description: String,
    val taskPriority: TaskPriority,
    val status: TaskStatus,
    val categoryId: Long,
    val dueDate: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
