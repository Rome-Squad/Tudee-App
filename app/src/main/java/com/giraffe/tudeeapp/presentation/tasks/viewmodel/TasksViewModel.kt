package com.giraffe.tudeeapp.presentation.tasks.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.domain.util.NotFoundError
import com.giraffe.tudeeapp.domain.util.Result
import com.giraffe.tudeeapp.domain.util.onError
import com.giraffe.tudeeapp.domain.util.onSuccess
import com.giraffe.tudeeapp.presentation.tasks.TasksArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime


class TasksViewModel(
    private val tasksService: TasksService,
    private val categoryService: CategoriesService,
    savedStateHandle: SavedStateHandle
) : ViewModel(), TasksScreenActions {

    private val _state = MutableStateFlow(
        TasksScreenState(
            selectedTab = TaskStatus.entries[TasksArgs(savedStateHandle).tabIndex]
        )
    )
    val state = _state.asStateFlow()

    init {
        val tabIndex = TasksArgs(savedStateHandle).tabIndex
        selectTab(TaskStatus.entries[tabIndex])
        getTasks(_state.value.pickedDate)
    }

    private fun getTasks(date: LocalDateTime) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksService.getTasksByDate(date)
                .onSuccess { flow ->
                    flow.collect { tasks ->
                        try {
                            val tasksUiList = tasks.map { task ->
                                val categoryResult =
                                    categoryService.getCategoryById(task.categoryId)
                                val category = if (categoryResult is Result.Success) {
                                    categoryResult.data
                                } else {
                                    null
                                }
                                task.toTaskUi(category ?: throw Exception())
                            }
                            val tasksMap = mapOf(
                                TaskStatus.TODO to tasksUiList.filter { it.status == TaskStatus.TODO },
                                TaskStatus.IN_PROGRESS to tasksUiList.filter { it.status == TaskStatus.IN_PROGRESS },
                                TaskStatus.DONE to tasksUiList.filter { it.status == TaskStatus.DONE },
                            )

                            _state.update { currentState ->
                                currentState.copy(
                                    tasks = tasksMap,
                                    pickedDate = date,
                                    error = null
                                )
                            }
                        } catch (_: Exception) {
                            _state.update { it.copy(error = NotFoundError()) }
                        }
                    }
                }
                .onError { error ->
                    _state.update { it.copy(error = error) }
                }
        }
    }

    override fun onAddTaskClick() {
        _state.update { currentState ->
            currentState.copy(isTaskEditorVisible = true, currentTaskId = null)
        }
    }


    override fun setPickedDate(date: LocalDateTime) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(pickedDate = date) }
            getTasks(date)
        }
    }

    override fun setDeleteBottomSheetVisibility(isVisible: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isDeleteBottomSheetVisible = isVisible) }
        }
    }

    override fun selectTab(tab: TaskStatus) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(selectedTab = tab) }
        }
    }

    override fun showSnackBarMessage(message: String, hasError: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    snackBarMsg = message,
                    isSnackBarVisible = true,
                    snackBarHasError = hasError
                )
            }
            delay(3000)
            _state.update {
                it.copy(
                    snackBarMsg = "",
                    isSnackBarVisible = false,
                    snackBarHasError = false
                )
            }
        }
    }

    override fun setSelectedTaskId(taskId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(selectedTaskId = taskId) }
        }
    }

    override fun deleteTask(taskId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksService.deleteTask(taskId)
                .onSuccess {
                    _state.update {
                        it.copy(
                            snackBarMsg = "Deleted task successfully.",
                            isSnackBarVisible = true,
                            isDeleteBottomSheetVisible = false,
                            tasks = it.tasks.mapValues { entry ->
                                entry.value.filter { task -> task.id != taskId }
                            }
                        )
                    }
                    delay(3000)
                    _state.update { it.copy(isSnackBarVisible = false) }
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            error = error,
                            isSnackBarVisible = true,
                            isDeleteBottomSheetVisible = false,
                        )
                    }
                    delay(3000)
                    _state.update { it.copy(isSnackBarVisible = false) }
                }
        }
    }


    override fun onTaskClick(taskId: Long) {
        _state.update { currentState ->
            currentState.copy(isTaskDetailsVisible = true, currentTaskId = taskId)
        }
    }

    override fun onEditTaskClick(taskId: Long?) {
        _state.update { currentState ->
            currentState.copy(isTaskEditorVisible = true, currentTaskId = taskId)
        }
    }

    override fun dismissTaskDetails() {
        _state.update { currentState ->
            currentState.copy(isTaskDetailsVisible = false, currentTaskId = null)
        }
    }

    override fun dismissTaskEditor() {
        _state.update { currentState ->
            currentState.copy(isTaskEditorVisible = false, currentTaskId = null)
        }
    }

}

