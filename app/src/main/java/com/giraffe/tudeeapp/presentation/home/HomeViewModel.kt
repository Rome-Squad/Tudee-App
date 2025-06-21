package com.giraffe.tudeeapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.domain.util.Result
import com.giraffe.tudeeapp.presentation.uimodel.toTaskUi
import com.giraffe.tudeeapp.presentation.utils.getCurrentLocalDateTime
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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

        val tasksResult = tasksService.getTasksByDate(getCurrentLocalDateTime())
        val categoriesResult = categoryService.getAllCategories()

        if (tasksResult is Result.Error) {
            _events.send(HomeEvent.Error(tasksResult.error))
            return@launch
        }

        if (categoriesResult is Result.Error) {

            _events.send(HomeEvent.Error(categoriesResult.error))
            return@launch
        }

        val tasksFlow = (tasksResult as Result.Success).data
        val categoriesFlow = (categoriesResult as Result.Success).data

        combine(tasksFlow, categoriesFlow) { tasks, categories ->
            val categoryMap = categories.associateBy { it.id }

            tasks.mapNotNull { task ->
                categoryMap[task.categoryId]?.let {
                    task.toTaskUi(it)
                }
            }

        }.collect { taskUiList ->
            val taskMap = TaskStatus.entries.associateWith { status ->
                taskUiList.filter { it.status == status }
            }

            _homeUiState.update { currentState ->
                currentState.copy(
                    tasks = taskMap,
                    isLoading = false
                )
            }

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