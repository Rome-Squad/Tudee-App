package com.giraffe.tudeeapp.presentation.taskdetails

sealed class TaskDetailsEffect {
    data class Error(val error: Throwable) : TaskDetailsEffect()
}