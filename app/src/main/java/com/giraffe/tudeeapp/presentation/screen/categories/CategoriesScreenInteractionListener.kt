package com.giraffe.tudeeapp.presentation.screen.categories

import com.giraffe.tudeeapp.domain.entity.Category

interface CategoriesScreenInteractionListener {
    fun selectCategory(categoryId: Long)
    fun setBottomSheetVisibility(isVisible: Boolean)
    fun addCategory(category: Category)
}