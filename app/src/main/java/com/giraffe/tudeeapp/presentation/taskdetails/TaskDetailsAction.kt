package com.giraffe.tudeeapp.presentation.taskdetails

import com.giraffe.tudeeapp.domain.model.task.TaskStatus

interface TaskDetailsAction {
    fun changeTaskStatus(newStatus: TaskStatus)
}