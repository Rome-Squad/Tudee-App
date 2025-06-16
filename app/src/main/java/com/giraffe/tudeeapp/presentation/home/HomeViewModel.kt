package com.giraffe.tudeeapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.domain.util.Result
import com.giraffe.tudeeapp.presentation.home.uistate.TasksUiState
import com.giraffe.tudeeapp.presentation.home.uistate.toUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val tasksService: TasksService,
    private val categoryService: CategoriesService
) : ViewModel() {
    private var _tasksUiState = MutableStateFlow(TasksUiState())
    val tasksUiState: StateFlow<TasksUiState> = _tasksUiState.asStateFlow()


//    init {
//        getAllTasks()
//    }


    private suspend fun getAllTasks() {
        _tasksUiState.update { it.copy(isLoading = true, errorMessage = null) }

        tasksService.getAllTasks().onEach { result ->
            _tasksUiState.update { currentState ->
                when (result) {
                    is Result.Success -> {
                        val tasks = result.data
                        val tasksUiList = tasks.map { task ->
                            val categoryResult = categoryService.getCategoryById(task.categoryId)
                            val category = if (categoryResult is Result.Success) {
                                categoryResult.data
                            } else null

                            task.toUiState(
                                categoryImage = category?.imageUri
                            )
                        }

                        val todoTasks =
                            tasksUiList.filter { it.taskStatusUi == TaskStatus.TODO }.take(2)

                        val doneTasks = tasksUiList.filter { it.taskStatusUi == TaskStatus.DONE }

                        val iProgressTasks =
                            tasksUiList.filter { it.taskStatusUi == TaskStatus.IN_PROGRESS }

                        currentState.copy(
                            todoTasks = todoTasks,
                            inProgressTasks = iProgressTasks,
                            doneTasks = doneTasks,
                            isLoading = false,
                            errorMessage = null
                        )
                    }

                    is Result.Error -> {
                        currentState.copy(isLoading = false, errorMessage = result.error)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

}