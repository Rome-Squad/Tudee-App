package com.giraffe.tudeeapp.presentation.tasks

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.domain.util.onError
import com.giraffe.tudeeapp.domain.util.onSuccess
import com.giraffe.tudeeapp.presentation.utils.toTaskUiList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

class TasksViewModel(
    private val tasksService: TasksService,
    private val categoryService: CategoriesService,
    savedStateHandle: SavedStateHandle
) : ViewModel(), TasksScreenActions {

    private val _state = MutableStateFlow(
        TasksScreenState(
            selectedTab = TaskStatus.entries[TasksArgs(savedStateHandle).tabIndex]
        )
    )
    val state = _state.asStateFlow()

    private val _events = Channel<TasksScreenEvent>()
    val events = _events.receiveAsFlow()

    init {
        val tabIndex = TasksArgs(savedStateHandle).tabIndex
        setSelectedTab(TaskStatus.entries[tabIndex])
        getTasks(_state.value.selectedDate)
    }

    private fun getTasks(date: LocalDateTime) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksService.getTasksByDate(date)
                .onError {
                    _events.send(TasksScreenEvent.Error(it))
                }
                .onSuccess { tasksFlow ->
                    categoryService.getAllCategories()
                        .onError {
                            _events.send(TasksScreenEvent.Error(it))
                        }
                        .onSuccess { categoriesFlow ->
                            combine(tasksFlow, categoriesFlow) { tasks, categories ->
                                tasks.toTaskUiList(categories)

                            }.collect { taskUiList ->

                                val taskMap = TaskStatus.entries.associateWith { status ->
                                    taskUiList.filter { it.status == status }
                                }
                                _state.update { currentState ->
                                    currentState.copy(
                                        tasks = taskMap,
                                        selectedDate = date,
                                    )
                                }
                            }
                        }
                }
        }
    }

    override fun setSelectedDate(date: LocalDateTime) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(selectedDate = date) }
            getTasks(date)
        }
    }

    override fun setSelectedTab(tab: TaskStatus) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(selectedTab = tab) }
        }
    }

    override fun setSelectedTaskId(taskId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(selectedTaskId = taskId) }
        }
    }

    override fun onTaskClick(taskId: Long) {
        _state.update { currentState ->
            currentState.copy(isTaskDetailsBottomSheetVisible = true, currentTaskId = taskId)
        }
    }

    override fun onDeleteTaskClick() {
        _state.update { currentState ->
            currentState.copy(isDeleteTaskBottomSheetVisible = true)
        }
    }

    override fun onAddTaskClick() {

      /*  _state.update { currentState ->
            currentState.copy(isTaskEditorBottomSheetVisible = true, currentTaskId = null,)

        }*/

        val selectedDate = _state.value.selectedDate
        _state.update {
            it.copy(
                isTaskEditorBottomSheetVisible = true,
                currentTaskId = null,
                taskEditorDate = selectedDate
            )

    }
        viewModelScope.launch {
            _events.send(TasksScreenEvent.OpenTaskEditor(selectedDate))
        }

    }

    override fun onEditTaskClick(taskId: Long?) {
        _state.update { currentState ->
            currentState.copy(isTaskEditorBottomSheetVisible = true, currentTaskId = taskId)
        }


    }
    fun setTaskEditorDate(date: LocalDateTime) {
        _state.update {
            it.copy(selectedDate = date)
        }
    }

    fun showTaskEditor() {
        _state.update {
            it.copy(isTaskEditorBottomSheetVisible = true)
        }
    }














    override fun onDismissTaskDetailsBottomSheetRequest() {
        _state.update { currentState ->
            currentState.copy(isTaskDetailsBottomSheetVisible = false, currentTaskId = null)
        }
    }

    override fun onDismissTaskEditorBottomSheetRequest() {
        _state.update { currentState ->
            currentState.copy(isTaskEditorBottomSheetVisible = false, currentTaskId = null)
        }
    }

    override fun onDismissDeleteTaskBottomSheetRequest() {
        _state.update { currentState ->
            currentState.copy(isDeleteTaskBottomSheetVisible = false, currentTaskId = null)
        }
    }

    override fun onConfirmDeleteTask(taskId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksService.deleteTask(taskId)
                .onSuccess {
                    onDismissDeleteTaskBottomSheetRequest()
                    _events.send(TasksScreenEvent.TaskDeletedSuccess)
                }
                .onError { error ->
                    onDismissDeleteTaskBottomSheetRequest()
                    _events.send(TasksScreenEvent.Error(error))
                }
        }
    }

}

