package com.giraffe.tudeeapp.data.mapper

import com.giraffe.tudeeapp.data.model.TaskEntity
import com.giraffe.tudeeapp.domain.model.task.Task
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.atTime


fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        uid = this.id,
        title = this.title,
        description = this.description,
        taskPriority = this.taskPriority,
        status = this.status,
        categoryId = this.categoryId,
        dueDate = this.dueDate.date.atTime(0, 0).toString(),
        createdAt = this.createdAt.date.atTime(0, 0).toString(),
        updatedAt = this.updatedAt.date.atTime(0, 0).toString()
    )
}

fun TaskEntity.toTask(): Task {
    return Task(
        id = this.uid,
        title = this.title,
        description = this.description,
        taskPriority = this.taskPriority,
        status = this.status,
        categoryId = this.categoryId,
        dueDate = LocalDateTime.parse(this.dueDate),
        createdAt = LocalDateTime.parse(this.createdAt),
        updatedAt = LocalDateTime.parse(this.updatedAt)
    )
}

