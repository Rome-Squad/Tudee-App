package com.giraffe.tudeeapp.presentation.screen.categories

import android.net.Uri
import com.giraffe.tudeeapp.domain.entity.Category

data class CategoriesScreenState(
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = true,
    val isBottomSheetVisible: Boolean = false,
    val selectedCategoryId: Long? = null,
    val selectedCategoryImageUri: Uri? = null,
    val selectedCategoryTitle: String? = null
)