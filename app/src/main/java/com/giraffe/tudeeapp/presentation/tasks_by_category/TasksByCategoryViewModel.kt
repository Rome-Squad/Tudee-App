package com.giraffe.tudeeapp.presentation.tasks_by_category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.domain.util.NotFoundError
import com.giraffe.tudeeapp.domain.util.Result
import com.giraffe.tudeeapp.domain.util.onError
import com.giraffe.tudeeapp.domain.util.onSuccess
import com.giraffe.tudeeapp.presentation.uimodel.toTaskUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TasksByCategoryViewModel(
    private val tasksService: TasksService,
    private val categoriesService: CategoriesService,
    savedStateHandle: SavedStateHandle
) : ViewModel(), TasksByCategoryScreenActions {
    private val _state = MutableStateFlow(TasksByCategoryScreenState())
    val state = _state.asStateFlow()

    private val _events = Channel<TasksByCategoryEvents>()
    val events = _events.receiveAsFlow()

    init {
        getCategoryById(CategoriesArgs(savedStateHandle = savedStateHandle).categoryId)
    }

    private fun getCategoryById(categoryId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            categoriesService.getCategoryById(categoryId)
                .onSuccess { category ->
                    _state.update { state ->
                        state.copy(
                            selectedCategory = category,
                        )
                    }
                    getTasks()
                }
                .onError { error ->
                    _state.update { it.copy(error = error) }
                }
        }
    }

    private fun getTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value.selectedCategory?.id?.let { categoryId ->
                tasksService.getTasksByCategory(categoryId)
                    .onSuccess { tasksFlow ->
                        tasksFlow.collect { tasks ->
                            try {
                                val tasksUiList = tasks.map { task ->
                                    val categoryResult =
                                        categoriesService.getCategoryById(task.categoryId)
                                    val category = if (categoryResult is Result.Success)
                                        categoryResult.data
                                    else
                                        null
                                    task.toTaskUi(
                                        category ?: throw Exception()
                                    )
                                }
                                _state.update {
                                    it.copy(
                                        tasks = mapOf(
                                            TaskStatus.TODO to tasksUiList.filter { it.status == TaskStatus.TODO },
                                            TaskStatus.IN_PROGRESS to tasksUiList.filter { it.status == TaskStatus.IN_PROGRESS },
                                            TaskStatus.DONE to tasksUiList.filter { it.status == TaskStatus.DONE },
                                        )
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
    }

    override fun setAlertBottomSheetVisibility(isVisible: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isAlertBottomSheetVisible = isVisible) }
        }
    }

    override fun setBottomSheetVisibility(isVisible: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isBottomSheetVisible = isVisible) }
        }
    }

    override fun selectTab(tab: TaskStatus) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(selectedTab = tab) }
        }
    }

    override fun editCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            categoriesService.updateCategory(category = category)
                .onSuccess {
                    _state.update {
                        it.copy(
                            selectedCategory = category,
                            isBottomSheetVisible = false,
                        )
                    }
                    _events.send(TasksByCategoryEvents.CategoryEdited())
                }.onError { error ->
                    _state.update {
                        it.copy(
                            error = error,
                            isBottomSheetVisible = false,
                        )
                    }
                    delay(3000)
                    _state.update { it.copy(error = null) }
                }
        }
    }

    override fun deleteCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            categoriesService.deleteCategory(category.id)
                .onSuccess {
                    _events.send(TasksByCategoryEvents.CategoryDeleted())
                    _state.update {
                        it.copy(
                            isBottomSheetVisible = false,
                        )
                    }
                }.onError { error ->
                    _state.update {
                        it.copy(
                            error = error,
                            isBottomSheetVisible = false,
                        )
                    }
                    delay(3000)
                    _state.update { it.copy(error = null) }
                }
        }
    }

    override fun setCategoryToDelete(category: Category?) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(categoryToDelete = category) }
        }
    }

    override fun showSnakeBarMsg(msg: String) {
        viewModelScope.launch {
            _state.update { it.copy(snakeBarMsg = msg) }
            delay(3000)
            _state.update { it.copy(snakeBarMsg = null) }
        }
    }
}