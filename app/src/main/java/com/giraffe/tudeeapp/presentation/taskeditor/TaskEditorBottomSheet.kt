package com.giraffe.tudeeapp.presentation.taskeditor


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.presentation.utils.EventListener
import com.giraffe.tudeeapp.presentation.utils.errorToMessage
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditorBottomSheet(
    taskId: Long?,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    selectedDate: LocalDate,
    onSuccess: (String) -> Unit = {},
    onError: (String) -> Unit = {},
    viewModel: TaskEditorViewModel = koinViewModel()
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val taskEditorUiState by viewModel.state.collectAsState()

    LaunchedEffect(key1 = taskId,selectedDate) {
        if (taskId == null) {
                viewModel.setDueDate(selectedDate)
            }
        else {
            viewModel.loadTask(taskId)
        }}
    EventListener(
        events = viewModel.effect,
    ) { event ->
        when (event) {
            TaskEditorEffect.TaskAddedSuccess -> onSuccess(context.getString(R.string.task_added_successfully))
            is TaskEditorEffect.Error -> onError(context.errorToMessage(event.error))
            TaskEditorEffect.TaskEditedSuccess -> onSuccess(context.getString(R.string.task_edited_successfully))
            TaskEditorEffect.DismissTaskEditor -> onDismissRequest()
        }

    }

    ModalBottomSheet(
        onDismissRequest = {
            viewModel.cancel()
            onDismissRequest()
            scope.launch { bottomSheetState.hide() }
        },
        sheetState = bottomSheetState,
        modifier = modifier.fillMaxHeight(0.95f),
        containerColor = Theme.color.surface
    ) {

        TaskEditorBottomSheetContent(
            taskEditorState = taskEditorUiState,
            actions  = viewModel,
            selectedDate = selectedDate,
            isNewTask = taskId == null
        )
    }


}

