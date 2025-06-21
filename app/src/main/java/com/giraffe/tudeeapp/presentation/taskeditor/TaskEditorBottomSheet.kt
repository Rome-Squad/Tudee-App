package com.giraffe.tudeeapp.presentation.taskeditor


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.presentation.utils.EventListener
import com.giraffe.tudeeapp.presentation.utils.errorToMessage
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditorBottomSheet(
    taskId: Long?,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onSuccess: (String) -> Unit = {},
    onError: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val storeOwner = remember(taskId) { ViewModelStore() }
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
            TaskEditorEvent.TaskAddedSuccess -> onSuccess(context.getString(R.string.task_added_successfully))
            is TaskEditorEvent.Error -> onError(context.errorToMessage(event.error))
            TaskEditorEvent.TaskEditedSuccess -> onSuccess(context.getString(R.string.task_edited_successfully))
            TaskEditorEvent.DismissTaskEditor -> onDismissRequest()
        }

    }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        onDismissRequest = {
            viewModel.cancel()
            onDismissRequest()
            scope.launch { bottomSheetState.hide() }
        },
        sheetState = bottomSheetState,
        modifier = modifier.statusBarsPadding(),
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

