package com.giraffe.tudeeapp.presentation.screen.taskeditor

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.CategoryItem
import com.giraffe.tudeeapp.design_system.component.DatePickerDialog
import com.giraffe.tudeeapp.design_system.component.DefaultTextField
import com.giraffe.tudeeapp.design_system.component.ParagraphTextField
import com.giraffe.tudeeapp.design_system.component.Priority
import com.giraffe.tudeeapp.design_system.component.button_type.PrimaryButton
import com.giraffe.tudeeapp.design_system.component.button_type.SecondaryButton
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.domain.entity.task.TaskPriority
import com.giraffe.tudeeapp.presentation.utils.EventListener
import com.giraffe.tudeeapp.presentation.utils.errorToMessage
import com.giraffe.tudeeapp.presentation.utils.formatAsLocalizedDate
import com.giraffe.tudeeapp.presentation.utils.toStringResource
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
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
    LaunchedEffect(key1 = taskId, selectedDate) {
        if (taskId == null) {
            viewModel.setDueDate(selectedDate)
        } else {
            viewModel.loadTask(taskId)
        }
    }
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
            actions = viewModel,
            selectedDate = selectedDate,
            isNewTask = taskId == null
        )
    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun TaskEditorBottomSheetContent(
    taskEditorState: TaskEditorState,
    selectedDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date,
    actions: TaskEditorInteractionListener,
    isNewTask: Boolean
) {

    val task = taskEditorState.task
    var showDatePickerDialog by remember { mutableStateOf(false) }

    DatePickerDialog(
        showDialog = showDatePickerDialog,
        onDismissRequest = {
            showDatePickerDialog = false
        },
        selectedDate = selectedDate,
        onDateSelected = { selectedDate ->
            actions.onChangeTaskDueDateValue(selectedDate)
            showDatePickerDialog = false
        }
    )

    AnimatedVisibility(visible = taskEditorState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surface),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surface)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Text(
                text = if (isNewTask) stringResource(R.string.add_task) else stringResource(R.string.edit_task),
                color = Theme.color.title,
                style = Theme.textStyle.title.large,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            DefaultTextField(
                textValue = task.title,
                onValueChange = {
                    if (it.length <= 30) {
                        actions.onChangeTaskTitleValue(it)
                    }
                },
                hint = stringResource(R.string.task_title),
                icon = painterResource(R.drawable.addeditfield)
            )

            Spacer(modifier = Modifier.height(16.dp))

            ParagraphTextField(
                textValue = task.description,
                onValueChange = {
                    if (it.length <= 100) {actions.onChangeTaskDescriptionValue(it)}},
                hint = stringResource(R.string.description)
            )

            Spacer(modifier = Modifier.height(16.dp))

            val context = LocalContext.current
            val formattedDate = task.dueDate.formatAsLocalizedDate(context)


            DefaultTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                isReadOnly = true,
                onClicked = { showDatePickerDialog = true },
                textValue = formattedDate,
                hint = stringResource(R.string.due_date_hint),
                icon = painterResource(R.drawable.calendar_plus)
            )


            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.priority),
                color = Theme.color.title,
                style = Theme.textStyle.title.medium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TaskPriority.entries.reversed().forEach { priority ->
                    val icon = when (priority) {
                        TaskPriority.HIGH -> R.drawable.flag
                        TaskPriority.MEDIUM -> R.drawable.alert
                        TaskPriority.LOW -> R.drawable.trade_down_icon
                    }
                    val selectedBackgroundColor = when (priority) {
                        TaskPriority.HIGH -> Theme.color.pinkAccent
                        TaskPriority.MEDIUM -> Theme.color.yellowAccent
                        TaskPriority.LOW -> Theme.color.greenAccent
                    }
                    Priority(
                        icon = painterResource(icon),
                        selectedBackgroundColor = selectedBackgroundColor,
                        label = priority.toStringResource(),
                        isSelected = task.taskPriority == priority,
                        modifier = Modifier
                            .clickable { actions.onChangeTaskPriorityValue(priority) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.category),
                color = Theme.color.title,
                style = Theme.textStyle.title.medium,
                modifier = Modifier.padding(bottom = 8.dp)
            )


            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 100.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp, max = 400.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                items(taskEditorState.categories) { category ->
                    val painter = rememberAsyncImagePainter(model = category.imageUri)

                    CategoryItem(
                        icon = painter,
                        categoryName = category.name,
                        isSelected = task.category.id == category.id,
                        count = 0,
                        isShowCount = false,
                        onClick = { actions.onChangeTaskCategoryValue(category.id) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme.color.surfaceHigh)
                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
        ) {
            PrimaryButton(
                text = if (isNewTask) stringResource(R.string.add) else stringResource(R.string.save),
                isLoading = taskEditorState.isLoading,
                isDisable = !taskEditorState.isValidTask,
                onClick = { if (isNewTask) actions.addTask(task) else actions.editTask(task) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(28.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            SecondaryButton(
                text = stringResource(R.string.cancel),
                isLoading = false,
                isDisable = false,
                onClick = actions::cancel,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}