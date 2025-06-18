package com.giraffe.tudeeapp.presentation.home.taskdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.Priority
import com.giraffe.tudeeapp.design_system.component.PriorityType
import com.giraffe.tudeeapp.design_system.component.button_type.NegativeTextButton
import com.giraffe.tudeeapp.design_system.component.button_type.SecondaryButton
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsBottomSheet(
    taskId: Long,
    onnDismiss: () -> Unit,
    onEditTask: (TaskUi?) -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    viewModel: TaskDetailsViewModel = koinViewModel { parametersOf(taskId) }
) {
    val task = viewModel.taskDetailsState.task
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onnDismiss
    ) {
        Column(
            modifier = modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment =Alignment.Start
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

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(100.dp))
                        .background(Theme.color.purpleVariant)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .size(5.dp)
                            .clip(CircleShape)
                            .background(Theme.color.purpleAccent)
                    )

                    Text(
                        text = task?.status?.name?.lowercase().toString(),
                        style = Theme.textStyle.body.small,
                        color = Theme.color.body
                    )
                }

                Priority(
                    priorityType = task?.priorityType ?: PriorityType.LOW,
                    isSelected = true
                )
            }

            if (task?.status != TaskStatus.DONE) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                ) {
                    SecondaryButton(
                        text = "",
                        icon = painterResource(R.drawable.ic_pencil_edit),
                    ) {
                        onEditTask(task)
                    }

                    NegativeTextButton(
                        text = if (task?.status == TaskStatus.TODO) "Move to in progress" else "Move to Done",
                    ) {
                        viewModel.changeTaskStatus(if (task?.status == TaskStatus.TODO) TaskStatus.IN_PROGRESS else TaskStatus.DONE)
                    }
                }
            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TaskDetailsBottomSheetPreview() {
}