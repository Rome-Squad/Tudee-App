package com.giraffe.tudeeapp.presentation.tasks

sealed class TasksScreenEffect {
    data class Error(val error: Throwable): TasksScreenEffect()
    object TaskDeletedSuccess: TasksScreenEffect()
}