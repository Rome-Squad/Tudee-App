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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.giraffe.tudeeapp.design_system.component.TudeeSnackBar
import com.giraffe.tudeeapp.presentation.home.componant.AddEditTaskContent
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    onDismissRequest: () -> Unit,
    headerTitle: String,
    saveButtonText: String,
    isEditMode: Boolean,
    addEditTaskViewModel: AddEditTaskViewModel = viewModel(),
    categoryViewModel: CategoryViewModel = viewModel()
) {
    val taskState by addEditTaskViewModel.taskState.collectAsState()
    val categories by categoryViewModel.categories.collectAsState()
    val categoriesLoading by categoryViewModel.isLoading.collectAsState()
    val isSuccess by addEditTaskViewModel.isSuccess.collectAsState()
    val error by addEditTaskViewModel.error.collectAsState()

    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var snackbarMessage by remember { mutableStateOf<String?>(null) }
    var snackbarType by remember { mutableStateOf<SnackbarType?>(null) }

    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            snackbarMessage = if (isEditMode) "Edited task successfully." else "Add task successfully."
            snackbarType = SnackbarType.SUCCESS

            kotlinx.coroutines.delay(500)
            scope.launch { bottomSheetState.hide() }
            onDismissRequest()
            addEditTaskViewModel.resetSuccess()
        }
    }

    LaunchedEffect(error) {
        error?.let {
            snackbarMessage = "Some error happened"
            snackbarType = SnackbarType.ERROR
        }
    }

    ModalBottomSheet(
        onDismissRequest = {
            onDismissRequest()
            scope.launch { bottomSheetState.hide() }
        },
        sheetState = bottomSheetState,
        modifier = Modifier.width(360.dp).height(690.dp),
    ) {
        AddEditTaskContent(
            headerTitle = headerTitle,
            saveButtonText = saveButtonText,
            taskState = taskState,
            categories = categories,
            isLoading = taskState.isLoading,
            categoriesLoading = categoriesLoading,
            onTitleChange = { addEditTaskViewModel.onTitleChange(it) },
            onDescriptionChange = { addEditTaskViewModel.onDescriptionChange(it) },
            onPriorityChange = { addEditTaskViewModel.onPriorityChange(it) },
            onStatusChange = { addEditTaskViewModel.onStatusChange(it) },
            onCategoryChange = { addEditTaskViewModel.onCategoryChange(it) },
            onDueDateChange = { millis ->
                val localDateTime = millis?.let {
                    Instant.fromEpochMilliseconds(it).toLocalDateTime(TimeZone.currentSystemDefault())
                } ?: Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

                addEditTaskViewModel.onDueDateChange(localDateTime)
            },
            onSaveClick = { addEditTaskViewModel.saveTask() },
            onCancelClick = {
                scope.launch { bottomSheetState.hide() }
                onDismissRequest()
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        snackbarMessage?.let { message ->
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 32.dp)
            ) {
                TudeeSnackBar(
                    message = message,
                    iconRes = when (snackbarType) {
                        SnackbarType.SUCCESS -> com.giraffe.tudeeapp.R.drawable.ic_success
                        SnackbarType.ERROR -> com.giraffe.tudeeapp.R.drawable.ic_error
                        else -> com.giraffe.tudeeapp.R.drawable.ic_error
                    },
                )
            }

            LaunchedEffect(message) {
                kotlinx.coroutines.delay(3000)
                snackbarMessage = null
                snackbarType = null
            }
        }
    }
}

enum class SnackbarType {
    SUCCESS, ERROR
}


