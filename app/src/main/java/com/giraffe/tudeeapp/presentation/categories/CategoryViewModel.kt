package com.giraffe.tudeeapp.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.util.Result
import com.giraffe.tudeeapp.presentation.categories.uistates.CategoriesUiState
import com.giraffe.tudeeapp.presentation.categories.uistates.toUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class CategoryViewModel(
    private val categoriesService: CategoriesService,
) : ViewModel() {


    private val _uiState = MutableStateFlow(CategoriesUiState())
    val uiState: StateFlow<CategoriesUiState> = _uiState.asStateFlow()

    init {
        getAllCategories()
    }

    private fun getAllCategories() {
        _uiState.update { it.copy(isLoading = true) }

        categoriesService.getAllCategories().onEach { result ->
            _uiState.update { currentState ->
                when (result) {
                    is Result.Success -> {
                        currentState.copy(
                            categories = result.data.map { category ->
                                category.toUiState(1)
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


    fun onClickCategory(categoryId: Long) {
    }

    fun onClickAddCategory() {

    }
}