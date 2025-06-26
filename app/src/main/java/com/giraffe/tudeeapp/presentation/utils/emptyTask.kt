package com.giraffe.tudeeapp.presentation.utils

import com.giraffe.tudeeapp.domain.entity.Category
import com.giraffe.tudeeapp.domain.entity.task.Task
import com.giraffe.tudeeapp.domain.entity.task.TaskPriority
import com.giraffe.tudeeapp.domain.entity.task.TaskStatus

fun emptyTask() = Task(
    id = 0L,
    title = "",
    description = "",
    taskPriority = TaskPriority.LOW,
    status = TaskStatus.TODO,
    category = Category(
        id = 0L,
        name = "",
        imageUri = "",
        isEditable = true,
        taskCount = 0
    ),
    dueDate = getCurrentLocalDate(),
    createdAt = getCurrentLocalDate(),
    updatedAt = getCurrentLocalDate()
)