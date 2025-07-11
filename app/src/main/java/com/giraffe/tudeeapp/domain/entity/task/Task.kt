package com.giraffe.tudeeapp.domain.entity.task

import com.giraffe.tudeeapp.domain.entity.Category
import kotlinx.datetime.LocalDate

data class Task(
    val id: Long = 0L,
    val title: String,
    val description: String,
    val taskPriority: TaskPriority,
    val status: TaskStatus,
    val category: Category,
    val dueDate: LocalDate,
    val createdAt: LocalDate,
    val updatedAt: LocalDate
)