package com.giraffe.tudeeapp.presentation.categories

import com.giraffe.tudeeapp.presentation.categories.uistates.CategoryUi

interface CategoriesScreenAction {
    fun selectCategory(categoryId: Long)
    fun setBottomSheetVisibility(isVisible: Boolean)
    fun addCategory(category: CategoryUi)
}