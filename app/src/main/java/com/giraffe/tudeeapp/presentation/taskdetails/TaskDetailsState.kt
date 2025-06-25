package com.giraffe.tudeeapp.presentation.taskdetails

import com.giraffe.tudeeapp.domain.entity.task.Task

data class TaskDetailsState(
    val task: Task? = null,
    val isLoading: Boolean = false
)
