package com.giraffe.tudeeapp.presentation.tasks

import com.giraffe.tudeeapp.domain.util.DomainError

sealed class TasksScreenEvent {
    data class Error(val error: DomainError): TasksScreenEvent()
    object TaskDeletedSuccess: TasksScreenEvent()
}