package com.giraffe.tudeeapp.presentation.categories

import com.giraffe.tudeeapp.domain.model.Category

interface CategoriesScreenActions {
    fun selectCategory(categoryId: Long)
    fun setBottomSheetVisibility(isVisible: Boolean)
    fun addCategory(category: Category)
}