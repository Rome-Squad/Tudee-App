package com.giraffe.tudeeapp.presentation.screen.tasksbycategory

import android.net.Uri
import com.giraffe.tudeeapp.domain.entity.Category
import com.giraffe.tudeeapp.domain.entity.task.TaskStatus

interface TasksByCategoryScreenInteractionListener {
    fun setAlertBottomSheetVisibility(isVisible: Boolean)
    fun setBottomSheetVisibility(isVisible: Boolean)
    fun selectTab(tab: TaskStatus)
    fun onSaveClick()
    fun deleteCategory(category: Category)
    fun onTitleChanged(title: String)
    fun onImageUriChanged(uri: Uri)
}