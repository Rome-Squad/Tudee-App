package com.giraffe.tudeeapp.presentation.taskdetails

import com.giraffe.tudeeapp.domain.entity.task.Task
import com.giraffe.tudeeapp.domain.entity.task.TaskStatus
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.presentation.base.BaseViewModel

class TaskDetailsViewModel(
    private val taskId: Long,
    val taskService: TasksService
) : BaseViewModel<TaskDetailsState, TaskDetailsEffect>(TaskDetailsState()), TaskDetailsInteractionListener {

    init {
        getTaskById(taskId)
    }

    fun getTaskById(taskId: Long) {
        updateState { currentState ->
            currentState.copy(isLoading = true)
        }
        safeExecute(
            onError = ::onGetTaskError,
            onSuccess = ::onGetTaskSuccess
        ) {
            taskService.getTaskById(taskId)
        }
    }

    private fun onGetTaskSuccess(task: Task) {
        updateState { currentState ->
            currentState.copy(
                task = task,
                isLoading = false
            )
        }
    }

    private fun onGetTaskError(error: Throwable) {
        updateState { currentState ->
            currentState.copy(
                isLoading = false
            )
        }
        sendEffect(TaskDetailsEffect.Error(error))
    }

    override fun changeTaskStatus(newStatus: TaskStatus) {
        updateState { currentState ->
            currentState.copy(isLoading = true)
        }
        if (state.value.task != null) {
            safeExecute(
                onError = ::onchangeStatusError,
                onSuccess = { onchangeStatusSuccess(newStatus) }
            ) {
                taskService.changeStatus(state.value.task!!.id, newStatus)
            }
        }
    }

    private fun onchangeStatusSuccess(newStatus: TaskStatus) {
        updateState { currentState ->
            currentState.copy(
                task = state.value.task!!.copy(
                    status = newStatus
                ),
                isLoading = false
            )
        }
    }

    private fun onchangeStatusError(error: Throwable) {
        updateState { currentState ->
            currentState.copy(
                isLoading = false
            )
        }
        sendEffect(TaskDetailsEffect.Error(error))
    }
}