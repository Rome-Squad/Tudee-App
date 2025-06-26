package com.giraffe.tudeeapp.presentation.tasks

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.entity.task.Task
import com.giraffe.tudeeapp.domain.entity.task.TaskStatus
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.presentation.base.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class TasksViewModel(
    private val tasksService: TasksService,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<TasksScreenState, TasksScreenEffect>(
    TasksScreenState(
        selectedTab = TaskStatus.entries[TasksArgs(savedStateHandle).tabIndex]
    )
), TasksScreenInteractionListener {

    init {
        val tabIndex = TasksArgs(savedStateHandle).tabIndex
        setSelectedTab(TaskStatus.entries[tabIndex])
        getTasks(state.value.selectedDate)
    }

    private fun getTasks(date: LocalDate) {
        safeCollect(
            onError = ::onGetTasksError,
            onEmitNewValue = { onGetTasksNewValue(it, date) }
        ) {
            tasksService.getTasksByDate(date)
        }
    }

    private fun onGetTasksError(error: Throwable) {
        sendEffect(TasksScreenEffect.Error(error))
    }

    private fun onGetTasksNewValue(tasks: List<Task>, date: LocalDate) {
        val taskMap = TaskStatus.entries.associateWith { status ->
            tasks.filter { it.status == status }
        }

        updateState { currentState ->
            currentState.copy(
                tasks = taskMap,
                selectedDate = date,
            )
        }
    }

    override fun setSelectedDate(date: LocalDate) {
        updateState { it.copy(selectedDate = date) }
        getTasks(date)
    }

    override fun setSelectedTab(tab: TaskStatus) {
        updateState { it.copy(selectedTab = tab) }
    }

    override fun setSelectedTaskId(taskId: Long) {
        updateState { it.copy(selectedTaskId = taskId) }
    }

    override fun onTaskClick(taskId: Long) {
        updateState { currentState ->
            currentState.copy(isTaskDetailsBottomSheetVisible = true, currentTaskId = taskId)
        }
    }

    override fun onDeleteTaskClick() {
        updateState { currentState ->
            currentState.copy(isDeleteTaskBottomSheetVisible = true)
        }
    }

    override fun onAddTaskClick() {
        val selectedDate = state.value.selectedDate

        updateState {
            it.copy(
                isTaskEditorBottomSheetVisible = true,
                currentTaskId = null,
                taskEditorDate = selectedDate
            )
        }

        viewModelScope.launch {
            sendEffect(TasksScreenEffect.OpenTaskEditor(selectedDate))
        }
    }

    override fun onEditTaskClick(taskId: Long?) {
        updateState { currentState ->
            currentState.copy(isTaskEditorBottomSheetVisible = true, currentTaskId = taskId)
        }
    }
    fun setTaskEditorDate(date: LocalDate) {
        updateState {
            it.copy(selectedDate = date)
        }
    }

    fun showTaskEditor() {
        updateState {
            it.copy(isTaskEditorBottomSheetVisible = true)
        }
    }

    override fun onDismissTaskDetailsBottomSheetRequest() {
        updateState { currentState ->
            currentState.copy(isTaskDetailsBottomSheetVisible = false, currentTaskId = null)
        }
    }

    override fun onDismissTaskEditorBottomSheetRequest() {
        updateState { currentState ->
            currentState.copy(isTaskEditorBottomSheetVisible = false, currentTaskId = null)
        }
    }

    override fun onDismissDeleteTaskBottomSheetRequest() {
        updateState { currentState ->
            currentState.copy(isDeleteTaskBottomSheetVisible = false, currentTaskId = null)
        }
    }

    override fun onConfirmDeleteTask(taskId: Long) {
        safeExecute(
            onError = ::onConfirmDeleteTaskError,
            onSuccess = { onConfirmDeleteTaskSuccess() }
        ) {
            tasksService.deleteTask(taskId)
        }
    }

    private fun onConfirmDeleteTaskError(error: Throwable) {
        onDismissDeleteTaskBottomSheetRequest()
        sendEffect(TasksScreenEffect.Error(error))
    }

    private fun onConfirmDeleteTaskSuccess() {
        onDismissDeleteTaskBottomSheetRequest()
        sendEffect(TasksScreenEffect.TaskDeletedSuccess)
    }

}

