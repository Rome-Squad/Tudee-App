package com.giraffe.tudeeapp.data.mapper

import com.giraffe.tudeeapp.data.model.TaskEntity
import com.giraffe.tudeeapp.domain.model.task.Task


fun Task.toData(): TaskEntity {
    return TaskEntity(
        uid = this.id,
        title = this.title,
        description = this.description,
        taskPriority = this.taskPriority,
        status = this.status,
        categoryId = this.categoryId,
        dueDate = this.dueDate,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}

fun TaskEntity.toDomain(): Task {
    return Task(
        id = this.uid,
        title = this.title,
        description = this.description,
        taskPriority = this.taskPriority,
        status = this.status,
        categoryId = this.categoryId,
        dueDate = this.dueDate,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}