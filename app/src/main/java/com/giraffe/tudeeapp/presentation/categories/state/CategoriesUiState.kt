package com.giraffe.tudeeapp.presentation.categories.state

import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.util.DomainError

data class CategoriesUiState(
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = true,
    val error: DomainError? = null,
    val isBottomSheetVisible: Boolean = false,
    val selectedCategoryId: Long? = null,
    val showSuccessSnackBar: Boolean = false
)
