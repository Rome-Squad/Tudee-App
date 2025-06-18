package com.giraffe.tudeeapp.presentation.home.taskdetails

import com.giraffe.tudeeapp.design_system.component.PriorityType
import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.model.task.Task
import com.giraffe.tudeeapp.domain.model.task.TaskPriority
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import kotlinx.datetime.LocalDateTime

data class TaskUi (
    val id: Long,
    val title: String,
    val description: String,
    val priorityType: PriorityType,
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
    priorityType = taskPriority.toPriorityType(),
    status = status,
    category = category,
    dueDate = dueDate,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun TaskPriority.toPriorityType() = when(this) {
    TaskPriority.LOW -> PriorityType.LOW
    TaskPriority.MEDIUM -> PriorityType.MEDIUM
    TaskPriority.HIGH -> PriorityType.HIGH
}