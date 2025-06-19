package com.giraffe.tudeeapp.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.domain.util.NotFoundError
import com.giraffe.tudeeapp.domain.util.Result
import com.giraffe.tudeeapp.domain.util.onError
import com.giraffe.tudeeapp.domain.util.onSuccess
import com.giraffe.tudeeapp.presentation.uimodel.toTaskUi
import com.giraffe.tudeeapp.presentation.utils.getCurrentLocalDateTime
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val tasksService: TasksService,
    private val categoryService: CategoriesService
) : ViewModel(), HomeActions {

    private var _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    private var _events = Channel<HomeEvent>()
    val events = _events.receiveAsFlow()

    init {
        getAllTasks()
    }

    private fun getAllTasks() = viewModelScope.launch {
        _homeUiState.update { it.copy(isLoading = true) }

        tasksService.getTasksByDate(getCurrentLocalDateTime())
            .onSuccess { tasksFlow ->
                tasksFlow.collect { tasks ->
                    try {
                        val tasksUiList = tasks.map { task ->
                            val categoryResult = categoryService.getCategoryById(task.categoryId)
                            val category = if (categoryResult is Result.Success)
                                categoryResult.data
                            else
                                null

                            task.toTaskUi(
                                category ?: throw Exception()
                            )

                        }

                        val todoTasks =
                            tasksUiList.filter { it.status == TaskStatus.TODO }

                        val doneTasks =
                            tasksUiList.filter { it.status == TaskStatus.DONE }

                        val iProgressTasks =
                            tasksUiList.filter { it.status == TaskStatus.IN_PROGRESS }

                        _homeUiState.update { currentState ->
                            currentState.copy(
                                allTasks = tasksUiList,
                                todoTasks = todoTasks,
                                inProgressTasks = iProgressTasks,
                                doneTasks = doneTasks,
                                isLoading = false,
                            )
                        }
                    } catch (_: Exception) {
                        _events.send(HomeEvent.Error(NotFoundError()))
                    }
                }
            }
            .onError { error ->
                _homeUiState.update { currentState ->
                    currentState.copy(isLoading = false)
                }
                _events.send(HomeEvent.Error(error))
            }

    }

    override fun onTasksLinkClick(tabIndex: Int) {
        viewModelScope.launch {
            clearUiState()
            _events.send(HomeEvent.NavigateToTasksScreen(tabIndex))
        }
    }


    override fun onAddTaskClick() {
        _homeUiState.update { currentState ->
            currentState.copy(isTaskEditorVisible = true, currentTaskId = null)
        }
    }

    override fun onTaskClick(taskId: Long) {
        _homeUiState.update { currentState ->
            currentState.copy(isTaskDetailsVisible = true, currentTaskId = taskId)
        }
    }

    override fun onEditTaskClick(taskId: Long?) {
        _homeUiState.update { currentState ->
            currentState.copy(isTaskEditorVisible = true, currentTaskId = taskId)
        }
    }

    override fun showTaskAddedSuccess(message: String) {
        viewModelScope.launch {
            _events.send(HomeEvent.TaskAddedSuccess)
        }
    }

    override fun showTaskEditedSuccess(message: String) {
        viewModelScope.launch {
            _events.send(HomeEvent.TaskEditedSuccess)
        }
    }

    override fun dismissTaskDetails() {
        _homeUiState.update { currentState ->
            currentState.copy(isTaskDetailsVisible = false, currentTaskId = null)
        }
        clearUiState()
    }

    override fun dismissTaskEditor() {
        _homeUiState.update { currentState ->
            currentState.copy(isTaskEditorVisible = false, currentTaskId = null)
        }
        clearUiState()
    }

    override fun dismissSnackBar() {
        viewModelScope.launch {
            _events.send(HomeEvent.DismissSnackBar)
        }
    }

    private fun clearUiState() {
        _homeUiState.update { currentState ->
            currentState.copy(
                isTaskDetailsVisible = false,
                isTaskEditorVisible = false,
                currentTaskId = null,
            )
        }
    }
}