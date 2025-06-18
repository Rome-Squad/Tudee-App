package com.giraffe.tudeeapp.presentation.categories.state

import com.giraffe.tudeeapp.domain.model.Category

interface CategoriesAction {
    fun selectCategory(categoryId: Long)
    fun setBottomSheetVisibility(isVisible: Boolean)
    fun addCategory(category: Category)
}