package com.giraffe.tudeeapp.presentation.home.componant

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.CategoryItem
import com.giraffe.tudeeapp.design_system.component.DefaultTextField
import com.giraffe.tudeeapp.design_system.component.ParagraphTextField
import com.giraffe.tudeeapp.design_system.component.Priority
import com.giraffe.tudeeapp.design_system.component.PriorityType
import com.giraffe.tudeeapp.design_system.component.button_type.TudeePrimaryButton
import com.giraffe.tudeeapp.design_system.component.button_type.TudeeSecondaryButton
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme
import com.giraffe.tudeeapp.domain.model.category.Category
import com.giraffe.tudeeapp.domain.model.task.TaskPriority
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.presentation.home.AddEditTaskUiState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddEditTaskContent(
    headerTitle: String,
    saveButtonText: String,
    taskState: AddEditTaskUiState,
    categories: List<Category>,
    isLoading: Boolean,
    categoriesLoading: Boolean,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPriorityChange: (TaskPriority) -> Unit,
    onStatusChange: (TaskStatus) -> Unit,
    onCategoryChange: (Long) -> Unit,
    onDueDateChange: (Long?) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    var showDatePickerDialog by remember { mutableStateOf(false) }

    TudeeDatePickerDialog(
        showDialog = showDatePickerDialog,
        onDismissRequest = { showDatePickerDialog = false },
        onDateSelected = { selectedDateMillis ->
            onDueDateChange(selectedDateMillis)
            showDatePickerDialog = false
        }
    )

    if (isLoading || categoriesLoading) {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(Theme.color.surface),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Theme.color.surface)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .align(Alignment.TopCenter)
            ) {
                // Header Title
                Text(
                    text = headerTitle,
                    color = Theme.color.title,
                    style = Theme.textStyle.title.large,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Title Input
                DefaultTextField(
                    textValue = taskState.title,
                    onValueChange = onTitleChange,
                    hint = "Task Title",
                    icon = painterResource(R.drawable.addeditfield)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Description Input
                ParagraphTextField(
                    textValue = taskState.description,
                    onValueChange = onDescriptionChange,
                    hint = "Description"
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier.clickable { showDatePickerDialog = true }
                ) {
                    DefaultTextField(
                        textValue = taskState.dueDateMillis?.let { millis ->
                            SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date(millis))
                        } ?: stringResource(R.string.date_picker_placeholder),
                        onValueChange = { },
                        hint = stringResource(R.string.due_date_hint),
                        icon = painterResource(R.drawable.calendar)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Priority",
                    color = Theme.color.title,
                    style = Theme.textStyle.title.medium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TaskPriority.values().forEach { priority ->
                        val priorityType = when (priority) {
                            TaskPriority.HIGH -> PriorityType.HIGH
                            TaskPriority.MEDIUM -> PriorityType.MEDIUM
                            TaskPriority.LOW -> PriorityType.LOW
                        }

                        Priority(
                            priorityType = priorityType,
                            isSelected = taskState.taskPriority == priority,
                            modifier = Modifier
                                .height(28.dp)
                                .width(56.dp)
                                .clickable { onPriorityChange(priority) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))


                Spacer(modifier = Modifier.height(16.dp))
                // Category Section
                Text(
                    text = "Category:",
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
                        .heightIn(max = 328.dp)
                ) {
                    items(items = categories, key = { it.id }) { category ->
                        val painter = painterResource(R.drawable.book_open_icon)
                        CategoryItem(
                            icon = painter,
                            categoryName = category.name,
                            isSelected = taskState.categoryId == category.id,
                            count = 0,
                            isShowCount = false,
                            onClick = { onCategoryChange(category.id) }
                        )
                    }
                }
            }

            // Bottom Buttons (Save, Cancel)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Theme.color.surfaceHigh)
                    .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
            ) {
                TudeePrimaryButton(
                    text = saveButtonText,
                    isLoading = false,
                    isDisable = false,
                    hasError = false,
                    onClick = onSaveClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(28.dp))
                )

                Spacer(modifier = Modifier.height(8.dp))

                TudeeSecondaryButton(
                    text = "Cancel",
                    isLoading = false,
                    isDisable = false,
                    onClick = onCancelClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 690)
@Composable
fun AddEditTaskModalBottomSheetPreview() {
    TudeeTheme(isDarkTheme = false) {
        val fakeState = AddEditTaskUiState(
            title = "Study desk task",
            description = "Solve all exercises.",
            taskPriority = TaskPriority.HIGH,
            taskStatus = TaskStatus.TODO,
            categoryId = 1,
            dueDateMillis = null,
            isLoading = false
        )
        val fakeCategories = listOf(
            Category(id = 1, name = "Study", imageUri = ""),
            Category(id = 2, name = "Shopping", imageUri = ""),
            Category(id = 3, name = "Health", imageUri = ""),
            Category(id = 4, name = "Work", imageUri = ""),
            Category(id = 5, name = "Personal", imageUri = ""),
            Category(id = 6, name = "Other", imageUri = "")
        )

        AddEditTaskContent(
            headerTitle = "Add Task",
            saveButtonText = "Save",
            taskState = fakeState,
            categories = fakeCategories,
            isLoading = false,
            categoriesLoading = false,
            onTitleChange = {},
            onDescriptionChange = {},
            onPriorityChange = {},
            onStatusChange = {},
            onCategoryChange = {},
            onDueDateChange = {},
            onSaveClick = {},
            onCancelClick = {}
        )
    }
}
