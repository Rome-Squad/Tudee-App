package com.giraffe.tudeeapp.presentation.categories

import com.giraffe.tudeeapp.domain.entity.Category

data class CategoriesScreenState(
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = true,
    val isBottomSheetVisible: Boolean = false,
    val selectedCategoryId: Long? = null,
)