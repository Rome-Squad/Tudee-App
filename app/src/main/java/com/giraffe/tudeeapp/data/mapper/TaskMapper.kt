package com.giraffe.tudeeapp.data.mapper

import com.giraffe.tudeeapp.data.dto.CategoryDto
import com.giraffe.tudeeapp.data.dto.TaskDto
import com.giraffe.tudeeapp.domain.entity.task.Task
import kotlinx.datetime.LocalDate


fun Task.toDto(): TaskDto {
    return TaskDto(
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

fun TaskDto.toEntity(category: CategoryDto): Task {
    return Task(
        id = this.uid,
        title = this.title,
        description = this.description,
        taskPriority = this.taskPriority,
        status = this.status,
        category = category.toEntity(),
        dueDate = LocalDate.parse(this.dueDate),
        createdAt = LocalDate.parse(this.createdAt),
        updatedAt = LocalDate.parse(this.updatedAt)
    )
}

fun List<TaskDto>.toEntityList(categories: List<CategoryDto>): List<Task> {
    val categoryMap = categories.associateBy { it.uid }
    return this.mapNotNull { task ->
        categoryMap[task.categoryId]?.let { category ->
            task.toEntity(category)
        }
    }
}
