package com.giraffe.tudeeapp.presentation.home.addedittask


import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
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
    modifier: Modifier = Modifier,
    viewModel: TaskEditorViewModel = koinViewModel { parametersOf(taskId) },
) {
    val density = LocalDensity.current
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val sheetTopPadding = statusBarPadding + 52.dp
    val maxSheetHeight = screenHeight - sheetTopPadding

    val taskState by viewModel.taskState.collectAsState()

    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)


    ModalBottomSheet(
        onDismissRequest = {
            onDismissRequest()
            scope.launch { bottomSheetState.hide() }
        },
        sheetState = bottomSheetState,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = maxSheetHeight),
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
            onDueDateChange = { millis ->
                viewModel.onDueDateChange(millisToLocalDateTime(millis))
            },
            onSaveClick = {
                viewModel.saveTask()
                onDismissRequest()
            },
            onCancelClick = {
                scope.launch { bottomSheetState.hide() }
                onDismissRequest()
            }
        )
    }


}

