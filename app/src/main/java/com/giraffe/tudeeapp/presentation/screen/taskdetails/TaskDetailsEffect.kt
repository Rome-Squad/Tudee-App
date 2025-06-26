package com.giraffe.tudeeapp.presentation.screen.taskdetails

sealed class TaskDetailsEffect {
    data class Error(val error: Throwable) : TaskDetailsEffect()
}