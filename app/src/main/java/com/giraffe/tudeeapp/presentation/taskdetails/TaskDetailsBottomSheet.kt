package com.giraffe.tudeeapp.presentation.taskdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import coil3.compose.rememberAsyncImagePainter
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.Priority
import com.giraffe.tudeeapp.design_system.component.button_type.SecondaryButton
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.domain.model.task.TaskPriority
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.presentation.utils.toStringResource
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsBottomSheet(
    taskId: Long,
    onnDismiss: () -> Unit,
    onEditTask: (Long?) -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
) {
    val storeOwner = remember(taskId) { ViewModelStore() }
    val owner = remember(taskId) {
        object : ViewModelStoreOwner {
            override val viewModelStore = storeOwner
        }
    }

    val viewModel: TaskDetailsViewModel = koinViewModel(
        viewModelStoreOwner = owner,
        parameters = { parametersOf(taskId) }
    )

    val task = viewModel.taskDetailsState.task
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            onnDismiss()
        },
        modifier = modifier,
        containerColor = Theme.color.surface
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(R.string.task_details),
                style = Theme.textStyle.title.large,
                color = Theme.color.title
            )

            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Theme.color.surfaceHigh),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(task?.category?.imageUri),
                    contentDescription = task?.title,
                )
            }

            Text(
                text = task?.title.toString(),
                style = Theme.textStyle.title.medium,
                color = Theme.color.title
            )

            Text(
                text = task?.description.toString(),
                style = Theme.textStyle.body.small,
                color = Theme.color.body
            )


            HorizontalDivider(
                thickness = 1.dp,
                color = Theme.color.stroke
            )

            var statusBackground = when (task?.status) {
                TaskStatus.TODO -> Theme.color.yellowVariant
                TaskStatus.IN_PROGRESS -> Theme.color.purpleVariant
                TaskStatus.DONE -> Theme.color.greenVariant
                else -> Theme.color.purpleVariant
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(100.dp))
                        .background(statusBackground)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(5.dp)
                            .clip(CircleShape)
                            .background(Theme.color.purpleAccent)
                    )

                    Text(
                        text = (task?.status ?: TaskStatus.DONE).toStringResource(),
                        style = Theme.textStyle.body.small,
                        color = Theme.color.purpleAccent
                    )

                }

                Priority(
                    priorityType = task?.priorityType ?: TaskPriority.LOW,
                    isSelected = true
                )
            }

            if (task?.status != TaskStatus.DONE) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .height(56.dp)
                            .border(1.dp, Theme.color.stroke, RoundedCornerShape(100.dp))
                            .clip(RoundedCornerShape(100.dp))
                            .padding(horizontal = 24.dp)
                            .clickable {
                                onEditTask(task?.id)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_pencil_edit),
                            contentDescription = stringResource(R.string.edit_task),
                            tint = Theme.color.primary
                        )
                    }

                    SecondaryButton(
                        text = if (task?.status == TaskStatus.TODO) stringResource(R.string.move_to_in_progress) else stringResource(
                            R.string.move_to_done
                        ),
                        modifier = Modifier
                            .padding(start = 8.dp)
                    ) {
                        viewModel.changeTaskStatus(if (task?.status == TaskStatus.TODO) TaskStatus.IN_PROGRESS else TaskStatus.DONE)
                    }
                }
            }

        }
    }
}