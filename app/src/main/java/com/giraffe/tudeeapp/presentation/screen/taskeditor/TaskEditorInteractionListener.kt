package com.giraffe.tudeeapp.presentation.screen.taskeditor

import com.giraffe.tudeeapp.domain.entity.task.Task
import com.giraffe.tudeeapp.domain.entity.task.TaskPriority
import com.giraffe.tudeeapp.domain.entity.task.TaskStatus
import kotlinx.datetime.LocalDate

interface TaskEditorInteractionListener {
    fun cancel()

    fun onChangeTaskTitleValue(title: String)
    fun onChangeTaskDescriptionValue(description: String)
    fun onChangeTaskDueDateValue(dueDate: LocalDate)
    fun onChangeTaskPriorityValue(priority: TaskPriority)
    fun onChangeTaskCategoryValue(categoryId: Long)
    fun onChangeTaskStatusValue(status: TaskStatus)
    fun addTask(task: Task)
    fun editTask(task: Task)

}