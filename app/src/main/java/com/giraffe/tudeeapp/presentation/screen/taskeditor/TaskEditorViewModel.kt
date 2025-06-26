package com.giraffe.tudeeapp.presentation.screen.taskeditor

import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.entity.Category
import com.giraffe.tudeeapp.domain.entity.task.Task
import com.giraffe.tudeeapp.domain.entity.task.TaskPriority
import com.giraffe.tudeeapp.domain.entity.task.TaskStatus
import com.giraffe.tudeeapp.domain.exceptions.NotFoundError
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.presentation.base.BaseViewModel
import com.giraffe.tudeeapp.presentation.utils.emptyTask
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class TaskEditorViewModel(
    private val tasksService: TasksService,
    private val categoriesService: CategoriesService,
) : BaseViewModel<TaskEditorState, TaskEditorEffect>(TaskEditorState()), TaskEditorInteractionListener {

    init {
        loadCategories()
    }

    private fun loadCategories() {
        updateState {
            it.copy(
                isLoading = true
            )
        }
        safeCollect(
            onError = ::onLoadCategoriesError,
            onEmitNewValue = ::onLoadCategoriesNewValue
        ) { 
            categoriesService.getAllCategories()
        }
    }
    
    private fun onLoadCategoriesError(error: Throwable) {
        updateState { it.copy(isLoading = false) }
        sendEffect(TaskEditorEffect.Error(error))
    }
    
    private fun onLoadCategoriesNewValue(categories: List<Category>) {
        updateState {
            it.copy(
                categories = categories,
                isLoading = false
            )
        }
    }

    fun loadTask(taskId: Long) {
        if (taskId == state.value.task.id) return
         updateState {
            it.copy(
                task = it.task.copy(id = taskId),
                isLoading = true
            )
        }
        safeExecute(
            onError = ::onLoadTaskError,
            onSuccess = ::onLoadTaskSuccess
        ) { 
            tasksService.getTaskById(taskId)
        }
    }
    
    private fun onLoadTaskError(error: Throwable) {
        updateState { it.copy(isLoading = false) }
        sendEffect(TaskEditorEffect.Error(error))
    }
    
    private fun onLoadTaskSuccess(task: Task) {
        updateState {
            it.copy(
                task = task,
                isLoading = false,
                isValidTask = isValidTask()
            )
        }
    }

    private fun getCategoryById(id: Long): Category? {
        return state.value.categories.find { category ->
            category.id == id
        }
    }


    override fun addTask(task: Task) {
        updateState {
            it.copy(
                isLoading = true
            )
        }
        safeExecute(
            onError = ::onAddTaskError,
            onSuccess = { onAddTaskSuccess() }
        ) { 
            tasksService.createTask(task)
        }
    }
    
    private fun onAddTaskError(error: Throwable) {
        updateState {
            it.copy(
                isLoading = false
            )
        }
        sendEffect(TaskEditorEffect.Error(error))
        sendEffect(TaskEditorEffect.DismissTaskEditor)
        clearTask()
    }

    private fun onAddTaskSuccess() {
        updateState {
            it.copy(
                isLoading = false
            )
        }
        sendEffect(TaskEditorEffect.TaskAddedSuccess)
        sendEffect(TaskEditorEffect.DismissTaskEditor)
        clearTask()
    }
    override fun editTask(task: Task) {
        updateState {
            it.copy(
                isLoading = true
            )
        }
        safeExecute(
            onError = ::onEditTaskError,
            onSuccess = { onEditTaskSuccess() }
        ) { 
            tasksService.updateTask(task)
        }
    }
    
    private fun onEditTaskError(error: Throwable) {
        updateState {
            it.copy(
                isLoading = false
            )
        }
        sendEffect(TaskEditorEffect.Error(error))
        sendEffect(TaskEditorEffect.DismissTaskEditor)
        clearTask()
    }
    
    private fun onEditTaskSuccess() {
        updateState {
            it.copy(
                isLoading = false
            )
        }
        sendEffect(TaskEditorEffect.TaskEditedSuccess)
        sendEffect(TaskEditorEffect.DismissTaskEditor)
        clearTask()
    }

    override fun cancel() {
        clearTask()
        sendEffect(TaskEditorEffect.DismissTaskEditor)
    }

    override fun onChangeTaskTitleValue(title: String) {
         updateState {
            it.copy(
                task = it.task.copy(title = title)
            )
        }
        validateTask()
    }

    override fun onChangeTaskDescriptionValue(description: String) {
         updateState {
            it.copy(
                task = it.task.copy(description = description)
            )
        }
        validateTask()
    }

    override fun onChangeTaskDueDateValue(dueDate: LocalDate) {
         updateState {
            it.copy(
                task = it.task.copy(dueDate = dueDate)
            )
        }
        validateTask()
    }

    override fun onChangeTaskPriorityValue(priority: TaskPriority) {
         updateState {
            it.copy(
                task = it.task.copy(taskPriority = priority)
            )
        }
        validateTask()
    }

    override fun onChangeTaskCategoryValue(categoryId: Long) {
        viewModelScope.launch {
            val category = getCategoryById(categoryId)
            if (category != null) {
                 updateState {
                    it.copy(
                        task = it.task.copy(category = category),
                        isLoading = false,
                        isValidTask = isValidTask()
                    )
                }
                validateTask()
            } else {
                 updateState {
                    it.copy(
                        isLoading = false
                    )
                }
                sendEffect(TaskEditorEffect.Error(NotFoundError()))
            }
        }
    }

    override fun onChangeTaskStatusValue(status: TaskStatus) {
         updateState {
            it.copy(
                task = it.task.copy(status = status)
            )
        }
    }

    private fun isValidTask(): Boolean {
        return !(state.value.task.title.isBlank() ||
                state.value.task.description.isBlank() ||
                state.value.task.category.name.isBlank() ||
                state.value.task.category.imageUri.isBlank()
                )
    }

    private fun validateTask() {
         updateState {
            it.copy(
                isValidTask = isValidTask()
            )
        }
    }

    private fun clearTask() {
         updateState {
            it.copy(
                task = emptyTask(),
                isLoading = false,
                isValidTask = false,
                isSuccessAdded = false,
                isSuccessEdited = false
            )
        }
    }


    fun setDueDate(date: LocalDate) {

        updateState{
            it.copy(
                task = it.task.copy(dueDate = date)
            )
        }

    }
}
