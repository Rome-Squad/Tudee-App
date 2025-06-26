package com.giraffe.tudeeapp.presentation.screen.taskdetails

import com.giraffe.tudeeapp.domain.entity.task.TaskStatus

interface TaskDetailsInteractionListener {
    fun changeTaskStatus(newStatus: TaskStatus)
}