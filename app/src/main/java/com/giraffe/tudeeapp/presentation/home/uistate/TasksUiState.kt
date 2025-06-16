package com.giraffe.tudeeapp.presentation.home.uistate

import com.giraffe.tudeeapp.domain.model.category.Category
import com.giraffe.tudeeapp.domain.model.task.Task
import com.giraffe.tudeeapp.domain.model.task.TaskPriority
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.util.DomainError

data class TasksUiState(
    val todoTasks: List<TaskUi> = emptyList(),
    val inProgressTasks: List<TaskUi> = emptyList(),
    val doneTasks: List<TaskUi> = emptyList(),
    val toDoTasksCount: Int = 0,
    val inProgressTasksCount: Int = 0,
    val doneTasksCount: Int = 0,
    val isLoading: Boolean = true,
    val errorMessage: DomainError? = null
)


data class TaskUi(
    val id: String,
    val categoryId: Long,
    val categoryImage: String?,
    val taskPriorityUi: TaskPriority,
    val taskStatusUi: TaskStatus,
    val taskTitle: String,
    val taskDescription: String,
)


// task counts
fun Task.toUiState(categoryImage: String? = null): TaskUi {
    return TaskUi(
        id = id.toString(),
        taskPriorityUi = taskPriority,
        taskTitle = title,
        taskDescription = description,
        taskStatusUi = status,
        categoryId = categoryId,
        categoryImage = categoryImage
    )
}

fun Category.toUiState(): CategoryImageUi {
    return CategoryImageUi(
        categoryImage = imageUri
    )
}

data class CategoryImageUi(
    val categoryImage: String?
)