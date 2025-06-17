package com.giraffe.tudeeapp.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.util.Result
import com.giraffe.tudeeapp.domain.util.onError
import com.giraffe.tudeeapp.domain.util.onSuccess
import com.giraffe.tudeeapp.presentation.categories.uiEvent.CategoriesUiEvent
import com.giraffe.tudeeapp.presentation.categories.uistates.CategoriesScreenUiState
import com.giraffe.tudeeapp.presentation.categories.uistates.CategoryUi
import com.giraffe.tudeeapp.presentation.categories.uistates.toUiState
import com.giraffe.tudeeapp.presentation.utils.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoriesService: CategoriesService,
) : ViewModel(), CategoriesScreenAction {

    private var _categoriesUiState = MutableStateFlow(CategoriesScreenUiState())
    val categoriesUiState: StateFlow<CategoriesScreenUiState> = _categoriesUiState.asStateFlow()

    private val _events = Channel<CategoriesUiEvent>()
    val events = _events.receiveAsFlow()


    init {
        getAllCategories()
    }

    private fun getAllCategories() {
        _categoriesUiState.update { it.copy(isLoading = true, error = null) }
        categoriesService.getAllCategories().onEach { result ->
            _categoriesUiState.update { currentState ->
                when (result) {
                    is Result.Success -> {
                        val categoryIds = result.data.map { it.id }
                        currentState.copy(
                            categories = result.data.map { category ->
                                category.toUiState(getTaskCountForCategory(category.id))
                            },
                            isLoading = false,
                            error = null
                        )
                    }

                    is Result.Error -> {
                        currentState.copy(
                            isLoading = false,
                            error = result.error
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTaskCountForCategory(categoryId: Long): Int {
        return 0
    }


    override fun selectCategory(categoryId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _events.send(CategoriesUiEvent.NavigateToTasksByCategoryScreen(categoryId))
        }
    }

    override fun setBottomSheetVisibility(isVisible: Boolean) {
        _categoriesUiState.update { it.copy(isBottomSheetVisible = isVisible) }
    }

    override fun addCategory(category: CategoryUi) {
        viewModelScope.launch(Dispatchers.IO) {
            categoriesService.createCategory(category.toEntity())
                .onSuccess {
                    getAllCategories()
                    _categoriesUiState.update {
                        it.copy(
                            showSuccessSnackBar = true,
                            isBottomSheetVisible = false
                        )
                    }
                    delay(3000)
                    _categoriesUiState.update { it.copy(showSuccessSnackBar = false) }
                }.onError { error ->
                    _categoriesUiState.update { it.copy(error = error) }
                }
        }
    }
}