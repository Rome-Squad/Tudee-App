package com.giraffe.tudeeapp.presentation.categories.tasks_by_category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.domain.util.onError
import com.giraffe.tudeeapp.domain.util.onSuccess
import com.giraffe.tudeeapp.presentation.categories.uistates.CategoryUi
import com.giraffe.tudeeapp.presentation.navigation.Screen
import com.giraffe.tudeeapp.presentation.utils.toCategory
import com.giraffe.tudeeapp.presentation.utils.toUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TasksByCategoryViewModel(
    private val tasksService: TasksService,
    private val categoriesService: CategoriesService,
    savedStateHandle: SavedStateHandle
) : ViewModel(), TasksByCategoryScreenActions {
    private val _state = MutableStateFlow(TasksByCategoryScreenState())
    val state = _state.asStateFlow()

    init {
        getCategoryById(savedStateHandle.get<Long>(Screen.TasksByCategoryScreen.CATEGORY_ID) ?: 0L)
        getTasks()
    }

    private fun getCategoryById(categoryId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            categoriesService.getCategoryById(categoryId)
                .onSuccess { category ->
                    _state.update { state ->
                        state.copy(
                            selectedCategory = category.toUiState(),
                        )
                    }
                }
                .onError { error ->
                    _state.update { it.copy(error = error) }
                }
        }
    }

    private fun getTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            tasksService.getTasksByCategory(_state.value.selectedCategory.id)
                .onSuccess { flow ->
                    flow.collect { tasks ->
                        _state.update { state ->
                            state.copy(
                                tasks = mapOf(
                                    TaskStatus.TODO to tasks.filter { it.status == TaskStatus.TODO },
                                    TaskStatus.IN_PROGRESS to tasks.filter { it.status == TaskStatus.IN_PROGRESS },
                                    TaskStatus.DONE to tasks.filter { it.status == TaskStatus.DONE },
                                )
                            )
                        }
                    }
                }
                .onError { error ->
                    _state.update { it.copy(error = error) }
                }
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

    override fun editCategory(category: CategoryUi) {
        viewModelScope.launch(Dispatchers.IO) {
            categoriesService.updateCategory(category = category.toCategory())
                .onSuccess {
                    _state.update {
                        it.copy(
                            showSuccessSnackBar = true,
                            isBottomSheetVisible = false
                        )
                    }
                    delay(3000)
                    _state.update { it.copy(showSuccessSnackBar = false) }
                }.onError { error ->
                    _state.update { it.copy(error = error) }
                }
        }
    }
}