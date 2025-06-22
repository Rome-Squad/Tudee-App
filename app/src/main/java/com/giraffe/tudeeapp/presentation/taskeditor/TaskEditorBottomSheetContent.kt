package com.giraffe.tudeeapp.presentation.taskeditor
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
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
import com.giraffe.tudeeapp.domain.model.task.TaskPriority
import com.giraffe.tudeeapp.presentation.uimodel.toTask
import com.giraffe.tudeeapp.presentation.utils.formatAsLocalizedDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskEditorBottomSheetContent(
    taskEditorUiState: TaskEditorUiState,
    actions : TaskEditorActions,
    isNewTask: Boolean
) {

    val taskUi = taskEditorUiState.taskUi
    var showDatePickerDialog by remember { mutableStateOf(false) }

    DatePickerDialog(
        showDialog = showDatePickerDialog,
        onDismissRequest = {
            showDatePickerDialog = false
        },
        onDateSelected = { selectedDateMillis ->
            actions.onChangeTaskDueDateValue(selectedDateMillis)
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
                textValue = taskUi.title,
                onValueChange = actions::onChangeTaskTitleValue,
                hint = stringResource(R.string.task_title),
                iconRes = R.drawable.addeditfield
            )

            Spacer(modifier = Modifier.height(16.dp))

            ParagraphTextField(
                textValue = taskUi.description,
                onValueChange = actions::onChangeTaskDescriptionValue,
                hint = stringResource(R.string.description)
            )

            Spacer(modifier = Modifier.height(16.dp))

            val context = LocalContext.current
            val formattedDate = taskUi.dueDate.date.formatAsLocalizedDate(context)


            DefaultTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { showDatePickerDialog = true },
                isReadOnly = true,
                textValue = formattedDate,
                hint = stringResource(R.string.due_date_hint),
                iconRes = R.drawable.calendar
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
                    Priority(
                        priorityType = priority,
                        isSelected = taskUi.priorityType == priority,
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

            val chunkedCategories = taskEditorUiState.categories.chunked(3)

            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                chunkedCategories.forEach { rowItems ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        rowItems.forEach { category ->
                            val painter = rememberAsyncImagePainter(model = category.imageUri)

                            CategoryItem(
                                icon = painter,
                                categoryName = category.name,
                                isSelected = taskUi.category.id == category.id,
                                count = 0,
                                isShowCount = false,
                                onClick = { actions.onChangeTaskCategoryValue(category.id) },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        repeat(3 - rowItems.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
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
                isLoading = taskEditorUiState.isLoading,
                isDisable = !taskEditorUiState.isValidTask,
                onClick = {if (isNewTask) actions.addTask(taskUi.toTask()) else actions.editTask(taskUi.toTask())},
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