package com.giraffe.tudeeapp.presentation.taskdetails

import com.giraffe.tudeeapp.domain.entity.task.TaskStatus

interface TaskDetailsInteractionListener {
    fun changeTaskStatus(newStatus: TaskStatus)
}