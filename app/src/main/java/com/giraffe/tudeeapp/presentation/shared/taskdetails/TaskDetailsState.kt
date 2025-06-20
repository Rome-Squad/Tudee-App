package com.giraffe.tudeeapp.presentation.shared.taskdetails

import com.giraffe.tudeeapp.domain.util.DomainError
import com.giraffe.tudeeapp.presentation.uimodel.TaskUi

data class TaskDetailsState(
    val task: TaskUi? = null,
    val isLoading: Boolean = false,
    val error: DomainError? = null
)
