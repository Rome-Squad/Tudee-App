package com.giraffe.tudeeapp.presentation.shared.taskeditor


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.presentation.shared.taskdetails.TaskDetailsViewModel
import com.giraffe.tudeeapp.presentation.utils.EventListener
import com.giraffe.tudeeapp.presentation.utils.errorToMessage
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditorBottomSheet(
    taskId: Long?,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onSuccess: (String) -> Unit = {},
    onError: (String) -> Unit = {}
) {
    val storeOwner = remember (taskId) { ViewModelStore() }
    val owner = remember(taskId) {
        object : ViewModelStoreOwner {
            override val viewModelStore = storeOwner
        }
    }


    val viewModel: TaskEditorViewModel = koinViewModel(
        viewModelStoreOwner = owner,
        parameters = { parametersOf(taskId) }
    )


    val taskEditorUiState by viewModel.taskEditorUiState.collectAsState()

    EventListener(
        events = viewModel.events,
    ) { event ->
        when (event) {
            TaskEditorEvent.TaskAddedSuccess -> onSuccess("Task Added Successfully")
            is TaskEditorEvent.Error -> onError(errorToMessage(event.error))
            TaskEditorEvent.TaskEditedSuccess -> onSuccess("Task Edited Successfully")
            TaskEditorEvent.DismissTaskEditor -> onDismissRequest()
        }

    }

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val maxSheetHeight = screenHeight - 72.dp
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = {
            viewModel.cancel()
            onDismissRequest()
            scope.launch { bottomSheetState.hide() }
        },
        sheetState = bottomSheetState,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = maxSheetHeight),
        containerColor = Theme.color.surface
    ) {

        TaskEditorBottomSheetContent(
            taskEditorUiState = taskEditorUiState,
            onTitleChange = viewModel::onChangeTaskTitleValue,
            onDescriptionChange = viewModel::onChangeTaskDescriptionValue,
            onPriorityChange = viewModel::onChangeTaskPriorityValue,
            onCategoryChange = viewModel::onChangeTaskCategoryValue,
            onDueDateChange = viewModel::onChangeTaskDueDateValue,
            onSaveClick = viewModel::saveTask,
            onCancelClick = viewModel::cancel,
            isNewTask = taskId == null
        )
    }


}

