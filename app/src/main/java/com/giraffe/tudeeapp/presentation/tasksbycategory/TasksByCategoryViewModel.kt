package com.giraffe.tudeeapp.presentation.tasksbycategory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.service.TasksService
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
                    getTasksByCategory(categoryId)
                }
                .onError { error ->
                    _state.update { it.copy(error = error) }
                }
        }
    }

    private fun getTasksByCategory(categoryId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            categoriesService.getCategoryById(categoryId)
                .onSuccess { category ->
                    getTasks(category)
                }
                .onError { error ->
                    _state.update { it.copy(error = error) }
                }
        }
    }

    private fun getTasks(category: Category) {
        viewModelScope.launch {
            tasksService.getTasksByCategory(category.id)
                .onSuccess { tasksFlow ->
                    tasksFlow.collect { tasks ->
                        tasks.map { task -> task.toTaskUi(category) }
                            .let { tasksUi ->
                                val tasks = TaskStatus.entries.associateWith { status ->
                                    tasksUi.filter { it.status == status }
                                }
                                _state.update { it.copy(tasks = tasks) }
                            }
                    }
                }
                .onError { error ->
                    _state.update { it.copy(error = error) }
                }
        }
    }

    override fun selectTab(tab: TaskStatus) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(selectedTab = tab) }
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

    override fun editCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            categoriesService.updateCategory(category = category)
                .onSuccess {
                    _state.update {
                        it.copy(
                            isBottomSheetVisible = false,
                            selectedCategory = category,
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
}