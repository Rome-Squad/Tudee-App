package com.giraffe.tudeeapp.presentation.home.addedittask
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
import com.giraffe.tudeeapp.design_system.component.DatePickerDialog
import com.giraffe.tudeeapp.design_system.component.DefaultTextField
import com.giraffe.tudeeapp.design_system.component.ParagraphTextField
import com.giraffe.tudeeapp.design_system.component.Priority
import com.giraffe.tudeeapp.design_system.component.button_type.PrimaryButton
import com.giraffe.tudeeapp.design_system.component.button_type.SecondaryButton
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme
import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.model.task.TaskPriority
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TaskEditorBottomSheetContent(
    headerTitle: String,
    saveButtonText: String,
    taskState: TaskEditorBottomSheetUiState,
    categories: List<Category>,
    isLoading: Boolean,
    categoriesLoading: Boolean,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPriorityChange: (TaskPriority) -> Unit,
    onCategoryChange: (Long) -> Unit,
    onDueDateChange: (Long?) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    var showDatePickerDialog by remember { mutableStateOf(false) }

    DatePickerDialog(
        showDialog = showDatePickerDialog,
        onDismissRequest = { showDatePickerDialog = false },
        onDateSelected = { selectedDateMillis ->
            onDueDateChange(selectedDateMillis)
            showDatePickerDialog = false
        }
    )

    if (isLoading || categoriesLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surface),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Theme.color.surface)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .align(Alignment.TopCenter)
            ) {
                Text(
                    text = headerTitle,
                    color = Theme.color.title,
                    style = Theme.textStyle.title.large,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                DefaultTextField(
                    textValue = taskState.title,
                    onValueChange = onTitleChange,
                    hint = "Task Title",
                    iconRes = R.drawable.addeditfield
                )

                Spacer(modifier = Modifier.height(16.dp))

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
                        textValue = taskState.dueDate.date.toString(),
                        onValueChange = { },
                        hint = stringResource(R.string.due_date_hint),
                        iconRes = R.drawable.calendar
                    )
                }

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
                        Priority(
                            priorityType = priority,
                            isSelected = taskState.taskPriority == priority,
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

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Theme.color.surfaceHigh)
                    .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
            ) {
                PrimaryButton(
                    text = saveButtonText,
                    isLoading = taskState.isLoading,
                    isDisable = !taskState.isValidInput,
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
}

@Preview(showBackground = true, widthDp = 360, heightDp = 690)
@Composable
fun TaskEditorBottomSheetContentPreview() {

    TudeeTheme(isDarkTheme = false) {
        val fakeCategories = listOf(
            Category(id = 1, name = "Study", imageUri = "", isEditable = false, taskCount = 0),
            Category(id = 2, name = "Shopping", imageUri = "", isEditable = false, taskCount = 0),
            Category(id = 3, name = "Health", imageUri = "", isEditable = false, taskCount = 0),
            Category(id = 4, name = "Work", imageUri = "", isEditable = false, taskCount = 0),
            Category(id = 5, name = "Personal", imageUri = "", isEditable = false, taskCount = 0),
            Category(id = 6, name = "Other", imageUri = "", isEditable = false, taskCount = 0),
        )

        fun validateInputs(state: TaskEditorBottomSheetUiState): Boolean {
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            return state.title.isNotBlank() &&
                    state.description.isNotBlank() &&
                    state.categoryId != null && state.dueDate.date >= now.date
        }

        var state by remember {
            mutableStateOf(
                TaskEditorBottomSheetUiState(
                    title = "Read a book",
                    description = "Read at least 20 pages.",
                    taskPriority = TaskPriority.HIGH,
                    taskStatus = TaskStatus.TODO,
                    categoryId = 1,
                    dueDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                    dueDateMillis = Clock.System.now().toEpochMilliseconds(),
                    categories = fakeCategories,
                    isLoading = false,
                ).let { initial ->
                    initial.copy(isValidInput = validateInputs(initial))
                }
            )
        }

        TaskEditorBottomSheetContent(
            headerTitle = "Add Task",
            saveButtonText = "Save",
            taskState = state,
            categories = fakeCategories,
            isLoading = false,
            categoriesLoading = false,
            onTitleChange = {
                state = state.copy(title = it).let { updated ->
                    updated.copy(isValidInput = validateInputs(updated))
                }
            },
            onDescriptionChange = {
                state = state.copy(description = it).let { updated ->
                    updated.copy(isValidInput = validateInputs(updated))
                }
            },
            onPriorityChange = {
                state = state.copy(taskPriority = it).let { updated ->
                    updated.copy(isValidInput = validateInputs(updated))
                }
            },
            onCategoryChange = {
                state = state.copy(categoryId = it).let { updated ->
                    updated.copy(isValidInput = validateInputs(updated))
                }
            },
            onDueDateChange = { newDueDateMillis ->
                state = state.copy(dueDateMillis = newDueDateMillis).let { updated ->
                    updated.copy(isValidInput = validateInputs(updated))
                }
            },

            onSaveClick = {},
            onCancelClick = {}
        )
    }
}
