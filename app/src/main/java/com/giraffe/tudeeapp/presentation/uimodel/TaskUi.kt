package com.giraffe.tudeeapp.presentation.uimodel

import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.model.task.Task
import com.giraffe.tudeeapp.domain.model.task.TaskPriority
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.presentation.utils.getCurrentLocalDateTime
import kotlinx.datetime.LocalDateTime

data class TaskUi (
    val id: Long = 0L,
    val title: String = "",
    val description: String = "",
    val priorityType: TaskPriority = TaskPriority.LOW,
    val status: TaskStatus = TaskStatus.TODO,
    val category: Category = Category(
        id = 0L,
        name = "",
        imageUri = "",
        isEditable = true,
        taskCount = 0
    ),
    val dueDate: LocalDateTime = getCurrentLocalDateTime(),
    val createdAt: LocalDateTime = getCurrentLocalDateTime(),
    val updatedAt: LocalDateTime = getCurrentLocalDateTime()
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