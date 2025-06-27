package com.giraffe.tudeeapp.presentation.screen.tasksbycategory

import com.giraffe.tudeeapp.domain.entity.Category
import com.giraffe.tudeeapp.domain.entity.task.TaskStatus

interface TasksByCategoryScreenInteractionListener {
    fun setAlertBottomSheetVisibility(isVisible: Boolean)
    fun setBottomSheetVisibility(isVisible: Boolean)
    fun selectTab(tab: TaskStatus)
    fun deleteCategory(category: Category)
    fun onSaveClick(title: String, imageUri: String)
}