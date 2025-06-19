package com.giraffe.tudeeapp.presentation.uimodel

import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.model.task.Task
import com.giraffe.tudeeapp.domain.model.task.TaskPriority
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import kotlinx.datetime.LocalDateTime

data class TaskUi (
    val id: Long,
    val title: String,
    val description: String,
    val priorityType: TaskPriority,
    val status: TaskStatus,
    val category: Category,
    val dueDate: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

fun Task.toTaskUi(category: Category) = TaskUi(
    id = id,
    title = title,
    description = description,
    priorityType = taskPriority,
    status = status,
    category = category,
    dueDate = dueDate,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun TaskUi.toTask() = Task(
    id = id,
    title = title,
    description = description,
    taskPriority = priorityType,
    status = status,
    categoryId = category.id,
    dueDate = dueDate,
    createdAt = createdAt,
    updatedAt = updatedAt
)