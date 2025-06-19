package com.giraffe.tudeeapp.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.domain.util.Result
import com.giraffe.tudeeapp.domain.util.onError
import com.giraffe.tudeeapp.domain.util.onSuccess
import com.giraffe.tudeeapp.presentation.uimodel.toTaskUi
import com.giraffe.tudeeapp.presentation.utils.errorToMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toLocalDateTime

class HomeViewModel(
    private val tasksService: TasksService,
    private val categoryService: CategoriesService
) : ViewModel() {
    private var _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    private val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    //2025-06-18T00:00
    init {
        getAllTasks()
    }

    private fun getAllTasks() = viewModelScope.launch {
        _homeUiState.update { it.copy(isLoading = true, errorMessage = null) }

        tasksService.getTasksByDate(currentDate)
            .onSuccess { tasksFlow ->
                tasksFlow.collect { tasks ->
                    Log.d("TAG", "getAllTasks:${currentDate.date.atTime(0, 0)} $tasks")
                    val tasksUiList = tasks.map { task ->
                        val categoryResult = categoryService.getCategoryById(task.categoryId)
                        val category = if (categoryResult is Result.Success) {
                            categoryResult.data
                        } else {
                            null
                        }
                        task.toTaskUi(
                            category ?: throw Exception()
                        )
                    }

                    val todoTasks =
                        tasksUiList.filter { it.status == TaskStatus.TODO }.take(2)

                    val doneTasks =
                        tasksUiList.filter { it.status == TaskStatus.DONE }.take(2)

                    val iProgressTasks =
                        tasksUiList.filter { it.status == TaskStatus.IN_PROGRESS }.take(2)

                    _homeUiState.update { currentState ->
                        Log.d("HomeViewModel", "getAllTasks: ${_homeUiState.value.allTasks}")
                        currentState.copy(
                            allTasks = tasksUiList,
                            todoTasks = todoTasks,
                            inProgressTasks = iProgressTasks,
                            doneTasks = doneTasks,
                            allTasksCount = tasksUiList.size,
                            toDoTasksCount = todoTasks.size,
                            inProgressTasksCount = iProgressTasks.size,
                            doneTasksCount = doneTasks.size,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
            }
            .onError { error ->
                Log.d("TAG", "getAllTasks: $error")
                _homeUiState.update { currentState ->
                    currentState.copy(isLoading = false, errorMessage = errorToMessage(error))
                }
            }

    }

    fun openAddEditTaskBottomSheet(taskId: Long?) {
        _homeUiState.update { currentState ->
            currentState.copy(
                isOpenAddEditTaskBottomSheet = true,
                currentTaskId = taskId,
                addEditBottomSheetToAdd = taskId == null
            )
        }
    }

    fun closeAddEditTaskBottomSheet() {
        _homeUiState.update { currentState ->
            currentState.copy(isOpenAddEditTaskBottomSheet = false, currentTaskId = null)
        }
    }

    fun openTaskDetails(taskId: Long) {
        _homeUiState.update { currentState ->
            currentState.copy(isOpenTaskDetailsBottomSheet = true, currentTaskId = taskId)
        }
    }

    fun closeTaskDetails() {
        _homeUiState.update { currentState ->
            currentState.copy(isOpenTaskDetailsBottomSheet = false, currentTaskId = null)
        }
    }

    fun showSnackBarSuccess() = viewModelScope.launch {
        Log.d("TAG", "showSnackBarSuccess: ${_homeUiState.value.isShowSnakbar}")
        _homeUiState.update { currentState ->
            currentState.copy(isShowSnakbar = true)
        }
        delay(3000)
        _homeUiState.update { currentState ->
            currentState.copy(isShowSnakbar = false)
        }
    }

    fun showSnackBarError(error: String?) = viewModelScope.launch {
        _homeUiState.update { currentState ->
            currentState.copy(isShowSnakbar = true, errorMessage = error)
        }
        delay(3000)
        _homeUiState.update { currentState ->
            currentState.copy(isShowSnakbar = false)
        }
    }
}