package com.giraffe.tudeeapp.data.mapper

import com.giraffe.tudeeapp.data.model.CategoryEntity
import com.giraffe.tudeeapp.data.model.TaskEntity
import com.giraffe.tudeeapp.domain.model.task.Task
import kotlinx.datetime.LocalDate


fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        uid = this.id,
        title = this.title,
        description = this.description,
        taskPriority = this.taskPriority,
        status = this.status,
        categoryId = this.category.id,
        dueDate = this.dueDate.toString(),
        createdAt = this.createdAt.toString(),
        updatedAt = this.updatedAt.toString()
    )
}

fun TaskEntity.toTask(category: CategoryEntity): Task {
    return Task(
        id = this.uid,
        title = this.title,
        description = this.description,
        taskPriority = this.taskPriority,
        status = this.status,
        category = category.toCategory(),
        dueDate = LocalDate.parse(this.dueDate),
        createdAt = LocalDate.parse(this.createdAt),
        updatedAt = LocalDate.parse(this.updatedAt)
    )
}

fun List<TaskEntity>.toTaskList(categories: List<CategoryEntity>): List<Task> {
    val categoryMap = categories.associateBy { it.uid }
    return this.mapNotNull { task ->
        categoryMap[task.categoryId]?.let { category ->
            task.toTask(category)
        }
    }
}
