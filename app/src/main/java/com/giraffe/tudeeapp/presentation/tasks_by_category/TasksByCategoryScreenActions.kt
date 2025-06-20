package com.giraffe.tudeeapp.presentation.tasks_by_category

import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.model.task.TaskStatus

interface TasksByCategoryScreenActions {
    fun setAlertBottomSheetVisibility(isVisible: Boolean)
    fun setBottomSheetVisibility(isVisible: Boolean)
    fun selectTab(tab: TaskStatus)
    fun editCategory(category: Category)
    fun deleteCategory(category: Category)
    fun setCategoryToDelete(category: Category?)
    fun showSnakeBarMsg(msg: String)
}