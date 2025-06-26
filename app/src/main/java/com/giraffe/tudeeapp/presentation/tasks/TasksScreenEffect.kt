package com.giraffe.tudeeapp.presentation.tasks

import kotlinx.datetime.LocalDate

sealed class TasksScreenEffect {
    data class Error(val error: Throwable): TasksScreenEffect()
    object TaskDeletedSuccess: TasksScreenEffect()
    data class OpenTaskEditor(val selectedDate: LocalDate) : TasksScreenEffect()

}