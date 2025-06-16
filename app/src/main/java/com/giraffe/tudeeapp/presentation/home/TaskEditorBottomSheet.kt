package com.giraffe.tudeeapp.presentation.home


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.TudeeSnackBar
import com.giraffe.tudeeapp.presentation.home.componant.TaskEditorBottomSheetContent
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditorBottomSheet(
    taskId: Long? = null,
    onDismissRequest: () -> Unit,
    headerTitle: String,
    saveButtonText: String,
    viewModel: TaskEditorBottomSheetViewModel = koinViewModel{ parametersOf(taskId) },
) {

    val taskState by viewModel.taskState.collectAsState()

    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)


    ModalBottomSheet(
        onDismissRequest = {
            onDismissRequest()
            scope.launch { bottomSheetState.hide() }
        },
        sheetState = bottomSheetState,
        modifier = Modifier
            .width(360.dp)
            .height(690.dp),
    ) {
        TaskEditorBottomSheetContent(
            headerTitle = headerTitle,
            saveButtonText = saveButtonText,
            taskState = taskState,
            categories = taskState.categories,
            isLoading = taskState.isLoadingTask ,
            categoriesLoading = taskState.isLoadingCategories,
            onTitleChange = { viewModel.onTitleChange(it) },
            onDescriptionChange = { viewModel.onDescriptionChange(it) },
            onPriorityChange = { viewModel.onPriorityChange(it) },
            onCategoryChange = { viewModel.onCategoryChange(it) },
            onDueDateChange = { millis ->
                val localDateTime = millis?.let {
                    Instant.fromEpochMilliseconds(it).toLocalDateTime(TimeZone.currentSystemDefault())
                } ?: Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

                viewModel.onDueDateChange(localDateTime)
            },
            onSaveClick = { viewModel.saveTask() },
            onCancelClick = {
                scope.launch { bottomSheetState.hide() }
                onDismissRequest()
            }
        )
    }


}

