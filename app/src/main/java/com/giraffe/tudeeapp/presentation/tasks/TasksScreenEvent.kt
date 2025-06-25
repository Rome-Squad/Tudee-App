package com.giraffe.tudeeapp.presentation.tasks

import com.giraffe.tudeeapp.domain.util.DomainError
import kotlinx.datetime.LocalDateTime

sealed class TasksScreenEvent {
    data class Error(val error: DomainError): TasksScreenEvent()
    object TaskDeletedSuccess: TasksScreenEvent()
    data class OpenTaskEditor(val selectedDate: LocalDateTime) : TasksScreenEvent()

}