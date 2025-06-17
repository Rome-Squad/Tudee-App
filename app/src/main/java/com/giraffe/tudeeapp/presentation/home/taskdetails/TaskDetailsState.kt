package com.giraffe.tudeeapp.presentation.home.taskdetails

data class TaskDetailsState(
    val task: TaskUi? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
