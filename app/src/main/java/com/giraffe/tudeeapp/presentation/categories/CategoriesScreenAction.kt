package com.giraffe.tudeeapp.presentation.categories

interface CategoriesScreenAction {
    fun selectCategory(categoryId: Long)
    fun setBottomSheetVisibility(isVisible: Boolean)
}