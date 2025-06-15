package com.giraffe.tudeeapp.presentation.home.taskdetails

import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.model.task.Task

data class TaskDetailsState(
    val task: TaskUi? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val message: String? = null
)
