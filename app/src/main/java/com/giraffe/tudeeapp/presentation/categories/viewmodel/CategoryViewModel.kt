package com.giraffe.tudeeapp.presentation.categories.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.util.onError
import com.giraffe.tudeeapp.domain.util.onSuccess
import com.giraffe.tudeeapp.presentation.categories.state.CategoriesAction
import com.giraffe.tudeeapp.presentation.categories.state.CategoriesUiEvent
import com.giraffe.tudeeapp.presentation.categories.state.CategoriesUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoriesService: CategoriesService,
) : ViewModel(), CategoriesAction {

    private var _categoriesUiState = MutableStateFlow(CategoriesUiState())
    val categoriesUiState: StateFlow<CategoriesUiState> = _categoriesUiState.asStateFlow()

    private val _events = Channel<CategoriesUiEvent>()
    val events = _events.receiveAsFlow()


    init {
        getAllCategories()
    }

    private fun getAllCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _categoriesUiState.update { it.copy(isLoading = true, error = null) }
            categoriesService.getAllCategories()
                .onSuccess { flow ->
                    flow.collect { categories ->
                        _categoriesUiState.update { currentState ->
                            currentState.copy(
                                categories = categories.map { category -> category },
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                }.onError { error ->
                    _categoriesUiState.update { it.copy(isLoading = false, error = error) }
                }

        }
    }

    override fun selectCategory(categoryId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _events.send(CategoriesUiEvent.NavigateToTasksByCategoryScreen(categoryId))
        }
    }

    override fun setBottomSheetVisibility(isVisible: Boolean) {
        _categoriesUiState.update { it.copy(isBottomSheetVisible = isVisible) }
    }

    override fun addCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            categoriesService.createCategory(category)
                .onSuccess {
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