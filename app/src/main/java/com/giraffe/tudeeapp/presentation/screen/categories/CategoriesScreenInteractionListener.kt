package com.giraffe.tudeeapp.presentation.screen.categories

interface CategoriesScreenInteractionListener {
    fun selectCategory(categoryId: Long)
    fun setBottomSheetVisibility(isVisible: Boolean)
    fun onAddNewCategory(title: String, imgUri: String)
}