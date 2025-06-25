package com.giraffe.tudeeapp.presentation.home

import com.giraffe.tudeeapp.domain.entity.task.Task
import com.giraffe.tudeeapp.domain.entity.task.TaskStatus
import com.giraffe.tudeeapp.domain.service.AppService
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.presentation.base.BaseViewModel
import com.giraffe.tudeeapp.presentation.utils.getCurrentLocalDate

class HomeViewModel(
    private val tasksService: TasksService,
    private val appService: AppService
) : BaseViewModel<HomeScreenState, HomeScreenEffect>(HomeScreenState()), HomeScreenInteractionListener {

    init {
        observeTheme()
        getTodayTasks()
    }
    private fun getTodayTasks() {
        updateState { it.copy(isLoading = true)}
        safeCollect(
            onError = ::onGetTodayTasksError,
            onEmitNewValue = ::onGetTodayTasksNewValue
        ) {
            tasksService.getTasksByDate(getCurrentLocalDate())
        }
    }

    private fun onGetTodayTasksNewValue(tasks: List<Task>) {
        val taskMap = TaskStatus.entries.associateWith { status ->
            tasks.filter { it.status == status }
        }

        updateState {
            it.copy(
                tasks = taskMap,
                isLoading = false
            )
        }
    }

    private fun onGetTodayTasksError(error: Throwable) {
        updateState { it.copy(isLoading = false) }
        sendEffect(HomeScreenEffect.Error(error))
    }

    override fun onTasksLinkClick(tabIndex: Int) {
        clearUiState()
        sendEffect(HomeScreenEffect.NavigateToTasksScreen(tabIndex))
    }

    override fun onAddTaskClick() {
        updateState { currentState ->
            currentState.copy(isTaskEditorVisible = true, currentTaskId = null)
        }
    }

    override fun onTaskClick(taskId: Long) {
        updateState { currentState ->
            currentState.copy(isTaskDetailsVisible = true, currentTaskId = taskId)
        }
    }

    override fun onEditTaskClick(taskId: Long?) {
        updateState { currentState ->
            currentState.copy(isTaskEditorVisible = true, currentTaskId = taskId)
        }
    }

    override fun onDismissTaskDetailsRequest() {
         updateState { currentState ->
            currentState.copy(isTaskDetailsVisible = false, currentTaskId = null)
        }
        clearUiState()
    }

    override fun onDismissTaskEditorRequest() {
         updateState { currentState ->
            currentState.copy(isTaskEditorVisible = false, currentTaskId = null)
        }
        clearUiState()
    }

    override fun onToggleTheme() {
        safeExecute {
            appService.setDarkThemeStatus(!state.value.isDarkTheme)
        }
    }

    private fun observeTheme() {
        safeCollect(
            onEmitNewValue = ::onGetCurrentThemeNewValue
        ) {
            appService.isDarkTheme()
        }
    }

    private fun onGetCurrentThemeNewValue(isDarkTheme: Boolean?) {
        updateState { currentState ->
            currentState.copy(isDarkTheme = isDarkTheme == true)
        }
    }

    private fun clearUiState() {
        updateState { currentState ->
            currentState.copy(
                isTaskDetailsVisible = false,
                isTaskEditorVisible = false,
                currentTaskId = null,
            )
        }
    }
}