package com.giraffe.tudeeapp.presentation.screen.taskdetails

import com.giraffe.tudeeapp.domain.entity.task.Task

data class TaskDetailsState(
    val task: Task? = null,
    val isLoading: Boolean = false
)
