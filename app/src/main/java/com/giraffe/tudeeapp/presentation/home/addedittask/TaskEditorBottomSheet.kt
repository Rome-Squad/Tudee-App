package com.giraffe.tudeeapp.presentation.home.addedittask


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.presentation.utils.millisToLocalDateTime
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditorBottomSheet(
    taskId: Long?,
    onDismissRequest: () -> Unit,
    headerTitle: String,
    saveButtonText: String,
    viewModel: TaskEditorViewModel = koinViewModel { parametersOf(taskId) },
) {

    val taskState by viewModel.taskState.collectAsState()

    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)


    ModalBottomSheet(
        onDismissRequest = {
            viewModel.onCancel()
            onDismissRequest()
            scope.launch { bottomSheetState.hide() }
        },
        sheetState = bottomSheetState,
        modifier = Modifier
            .fillMaxWidth(),
        containerColor = Theme.color.surface
    ) {
        TaskEditorBottomSheetContent(
            headerTitle = headerTitle,
            saveButtonText = saveButtonText,
            taskState = taskState,
            categories = taskState.categories,
            isLoading = taskState.isLoading,
            categoriesLoading = taskState.isLoading,
            onTitleChange = { viewModel.onTitleChange(it) },
            onDescriptionChange = { viewModel.onDescriptionChange(it) },
            onPriorityChange = { viewModel.onPriorityChange(it) },
            onCategoryChange = { viewModel.onCategoryChange(it) },
            onDueDateChange = viewModel::onDueDateChange
            ,
            onSaveClick = {
                viewModel.saveTask()
                onDismissRequest()
            },
            onCancelClick = {
                viewModel.onCancel()
                scope.launch { bottomSheetState.hide() }
                onDismissRequest()
            }
        )
    }


}

