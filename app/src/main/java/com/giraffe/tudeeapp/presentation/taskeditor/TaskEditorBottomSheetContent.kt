package com.giraffe.tudeeapp.presentation.taskeditor
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.CategoryItem
import com.giraffe.tudeeapp.design_system.component.DatePickerDialog
import com.giraffe.tudeeapp.design_system.component.DefaultTextField
import com.giraffe.tudeeapp.design_system.component.ParagraphTextField
import com.giraffe.tudeeapp.design_system.component.Priority
import com.giraffe.tudeeapp.design_system.component.button_type.PrimaryButton
import com.giraffe.tudeeapp.design_system.component.button_type.SecondaryButton
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.domain.model.task.TaskPriority
import com.giraffe.tudeeapp.presentation.taskeditor.TaskEditorUiState
import com.giraffe.tudeeapp.presentation.utils.millisToLocalDateTime
import kotlinx.datetime.LocalDateTime

@Composable
fun TaskEditorBottomSheetContent(
    taskEditorUiState: TaskEditorUiState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPriorityChange: (TaskPriority) -> Unit,
    onCategoryChange: (Long) -> Unit,
    onDueDateChange: (LocalDateTime) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    isNewTask: Boolean
) {
    val taskUi = taskEditorUiState.taskUi
    var showDatePickerDialog by remember { mutableStateOf(false) }

    DatePickerDialog(
        showDialog = showDatePickerDialog,
        onDismissRequest = {
            showDatePickerDialog = false },
        onDateSelected = { selectedDateMillis ->
            onDueDateChange(millisToLocalDateTime(selectedDateMillis))
            showDatePickerDialog = false
        }
    )

    AnimatedVisibility(visible = taskEditorUiState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surface),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme.color.surface)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(bottom = 120.dp, top = 16.dp)
                .align(Alignment.TopCenter)
        ) {
            Text(
                ////////
                text = if (isNewTask) stringResource(R.string.add_task) else stringResource(R.string.edit_task),
                color = Theme.color.title,
                style = Theme.textStyle.title.large,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            DefaultTextField(
                textValue = taskUi.title,
                onValueChange = onTitleChange,
                hint = "Task Title",
                iconRes = R.drawable.addeditfield
            )

            Spacer(modifier = Modifier.height(16.dp))

            ParagraphTextField(
                textValue = taskUi.description,
                onValueChange = onDescriptionChange,
                hint = "Description"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePickerDialog = true }
            ) {
                DefaultTextField(
                    textValue = taskUi.dueDate.date.toString(),
                    onValueChange = { },
                    //
                    hint = stringResource(R.string.due_date_hint ),
                    iconRes = R.drawable.calendar
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                //
                text = stringResource(R.string.periorty),
                color = Theme.color.title,
                style = Theme.textStyle.title.medium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TaskPriority.entries.reversed().forEach { priority ->
                    Priority(
                        priorityType = priority,
                        isSelected = taskUi.priorityType == priority,
                        modifier = Modifier
                            .height(28.dp)
                            .width(56.dp)
                            .clickable { onPriorityChange(priority) }
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
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 328.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(items = taskEditorUiState.categories, key = { it.id }) { category ->
                    val painter = painterResource(R.drawable.book_open_icon)
                    CategoryItem(
                        icon = painter,
                        categoryName = category.name,
                        isSelected = taskUi.category.id == category.id,
                        count = 0,
                        isShowCount = false,
                        onClick = { onCategoryChange(category.id) }
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Theme.color.surfaceHigh)
                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
        ) {
            PrimaryButton(
                text = if (isNewTask) stringResource(R.string.add) else stringResource(R.string.save),
                isLoading = taskEditorUiState.isLoading,
                isDisable = !taskEditorUiState.isValidTask,
                onClick = onSaveClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(28.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            SecondaryButton(
                text = "Cancel",
                isLoading = false,
                isDisable = false,
                onClick = onCancelClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}