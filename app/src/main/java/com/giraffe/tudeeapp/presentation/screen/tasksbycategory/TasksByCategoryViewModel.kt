package com.giraffe.tudeeapp.presentation.screen.tasksbycategory

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import com.giraffe.tudeeapp.domain.entity.Category
import com.giraffe.tudeeapp.domain.entity.task.Task
import com.giraffe.tudeeapp.domain.entity.task.TaskStatus
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.presentation.base.BaseViewModel
import com.giraffe.tudeeapp.presentation.screen.tasksbycategory.TasksByCategoryEffect.CategoryDeleted
import com.giraffe.tudeeapp.presentation.screen.tasksbycategory.TasksByCategoryEffect.CategoryEdited
import com.giraffe.tudeeapp.presentation.screen.tasksbycategory.TasksByCategoryEffect.DeleteCategoryError
import com.giraffe.tudeeapp.presentation.screen.tasksbycategory.TasksByCategoryEffect.EditCategoryError
import com.giraffe.tudeeapp.presentation.screen.tasksbycategory.TasksByCategoryEffect.GetCategoryError
import com.giraffe.tudeeapp.presentation.screen.tasksbycategory.TasksByCategoryEffect.GetTasksError


class TasksByCategoryViewModel(
    private val tasksService: TasksService,
    private val categoriesService: CategoriesService,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<TasksByCategoryScreenState, TasksByCategoryEffect>(TasksByCategoryScreenState()),
    TasksByCategoryScreenInteractionListener {

    init {
        getCategoryById(CategoriesArgs(savedStateHandle = savedStateHandle).categoryId)
    }

    private fun getCategoryById(categoryId: Long) {
        safeExecute(
            onError = ::onGetCategoryError,
            onSuccess = ::onGetCategorySuccess
        ) {
            categoriesService.getCategoryById(categoryId)
        }
    }

    private fun onGetCategoryError(error: Throwable) {
        sendEffect(GetCategoryError(error))
    }

    private fun onGetCategorySuccess(category: Category) {
        updateState { state ->
            state.copy(
                selectedCategory = category,
                selectedCategoryTitle = category.name,
                selectedCategoryImageUri = category.imageUri
            )
        }
        getTasks(category)
    }

    private fun getTasks(category: Category) {
        safeCollect(
            onError = ::onGetTasksError,
            onEmitNewValue = ::onGetTasksNewValue
        ) {
            tasksService.getTasksByCategory(category.id)
        }
    }

    private fun onGetTasksError(error: Throwable) {
        sendEffect(GetTasksError(error))
    }

    private fun onGetTasksNewValue(tasks: List<Task>) {
        val tasksInMap = TaskStatus.entries.associateWith { status ->
            tasks.filter { it.status == status }
        }
        updateState { it.copy(tasks = tasksInMap) }
    }

    override fun selectTab(tab: TaskStatus) {
        updateState { it.copy(selectedTab = tab) }
    }

    override fun setAlertBottomSheetVisibility(isVisible: Boolean) {
        updateState { it.copy(isAlertBottomSheetVisible = isVisible) }
    }

    override fun setBottomSheetVisibility(isVisible: Boolean) {
        updateState { it.copy(isBottomSheetVisible = isVisible) }
    }

    override fun onSaveClick() {
        state.value.selectedCategory?.copy(
            name = state.value.selectedCategoryTitle ?: "",
            imageUri = state.value.selectedCategoryImageUri ?: ""
        )?.let { category ->
            safeExecute(
                onError = ::onEditCategoryError,
                onSuccess = { onEditCategorySuccess(category) }
            ) {
                categoriesService.updateCategory(category)
            }
        }
    }

    private fun onEditCategoryError(error: Throwable) {
        sendEffect(EditCategoryError(error))
    }

    private fun onEditCategorySuccess(category: Category) {
        updateState {
            it.copy(
                isBottomSheetVisible = false,
                selectedCategory = category,
            )
        }
        sendEffect(CategoryEdited())
    }

    override fun deleteCategory(category: Category) {
        safeExecute(
            onError = ::onDeleteCategoryError,
            onSuccess = { onDeleteCategorySuccess() }
        ) {
            categoriesService.deleteCategory(category.id)
        }
    }

    override fun onTitleChanged(title: String) {
        updateState { it.copy(selectedCategoryTitle = title) }
    }

    override fun onImageUriChanged(uri: Uri) {
        updateState { it.copy(selectedCategoryImageUri = uri.toString()) }
    }

    private fun onDeleteCategoryError(error: Throwable) {
        sendEffect(DeleteCategoryError(error))
    }

    private fun onDeleteCategorySuccess() {
        updateState {
            it.copy(
                isBottomSheetVisible = false,
            )
        }
        sendEffect(CategoryDeleted())
    }
}