package com.giraffe.tudeeapp.presentation.categories

import com.giraffe.tudeeapp.presentation.categories.uistates.CategoryUi

interface CategoriesScreenAction {
    fun selectCategory(category: CategoryUi)
    fun setBottomSheetVisibility(isVisible: Boolean)
    fun addCategory(category: CategoryUi)
}