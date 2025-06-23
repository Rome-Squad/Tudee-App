package com.giraffe.tudeeapp.presentation.taskdetails

import com.giraffe.tudeeapp.domain.util.DomainError

sealed class TaskDetailsEvent {
    data class Error(val error: DomainError) : TaskDetailsEvent()
}