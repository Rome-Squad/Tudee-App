package com.giraffe.tudeeapp.presentation.categories

import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.util.DomainError

data class CategoriesScreenState(
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = true,
    val error: DomainError? = null,
    val isBottomSheetVisible: Boolean = false,
    val selectedCategoryId: Long? = null,
    val isSnackBarVisible: Boolean = false
)