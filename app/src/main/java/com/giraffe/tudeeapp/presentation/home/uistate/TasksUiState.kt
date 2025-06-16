package com.giraffe.tudeeapp.presentation.home.uistate

import com.giraffe.tudeeapp.domain.model.task.Task
import com.giraffe.tudeeapp.domain.model.task.TaskPriority
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.util.DomainError

data class TasksUiState(
    val tasks: List<TaskUi>,
    val isLoading: Boolean = true,
    val errorMessage: DomainError? = null
)

data class TaskUi(
    val id: String,
//    val imageUri: String,
    val taskPriorityUi: TaskPriorityUi,
    val taskStatusUi: TaskStatusUi,
    val taskTitle: String,
    val taskDescription: String,
)

enum class TaskStatusUi { TODO, IN_PROGRESS, DONE }

enum class TaskPriorityUi { LOW, MEDIUM, HIGH }

fun TaskPriority.toUiState(): TaskPriorityUi {
    return when (this) {
        TaskPriority.LOW -> TaskPriorityUi.LOW
        TaskPriority.MEDIUM -> TaskPriorityUi.MEDIUM
        TaskPriority.HIGH -> TaskPriorityUi.HIGH
    }
}

fun TaskStatus.toUiState(): TaskStatusUi {
    return when (this) {
        TaskStatus.TODO -> TaskStatusUi.TODO
        TaskStatus.IN_PROGRESS -> TaskStatusUi.IN_PROGRESS
        TaskStatus.DONE -> TaskStatusUi.DONE
    }
}

// task counts and images
fun Task.toUiState(): TaskUi {
    return TaskUi(
        id = id.toString(),
        taskPriorityUi = taskPriority.toUiState(),
        taskTitle = title,
        taskDescription = description,
        taskStatusUi = status.toUiState()
    )
}