package com.giraffe.tudeeapp.data.mapper

import com.giraffe.tudeeapp.domain.model.task.Task as DomainTask
import com.giraffe.tudeeapp.data.Task as DataTask

fun DomainTask.toData(): DataTask {
    return DataTask(
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

fun DataTask.toDomain(): DomainTask {
    return DomainTask(
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