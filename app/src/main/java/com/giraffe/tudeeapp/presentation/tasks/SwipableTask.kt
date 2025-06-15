package com.giraffe.tudeeapp.presentation.tasks

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.PriorityType
import com.giraffe.tudeeapp.design_system.component.TaskCard
import com.giraffe.tudeeapp.design_system.component.TaskCardType
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.domain.model.task.Task
import com.giraffe.tudeeapp.domain.model.task.TaskPriority
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlin.math.roundToInt


@Composable
fun SwipableTask(
    task: Task,
    modifier: Modifier = Modifier,
    isRevealed: Boolean = false,
    onExpanded: () -> Unit = {},
    onCollapsed: () -> Unit = {}
) {
    var cardWidth by remember { mutableFloatStateOf(0f) }
    val offset = remember {
        Animatable(initialValue = 0f)
    }
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = isRevealed, cardWidth) {
        if (isRevealed) {
            offset.animateTo(cardWidth)
        } else {
            offset.animateTo(0f)
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
//            .height(IntrinsicSize.Min)
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .fillMaxHeight()
                .onSizeChanged { cardWidth = it.width.toFloat() }
        ) {
            TaskDeleteButton(
                onClick = {},
                modifier = Modifier
            )
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(offset.value.roundToInt(), 0) }
                .pointerInput(true) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            scope.launch {
                                val newOffset = (offset.value + dragAmount).coerceIn(-cardWidth, 0f)
                                offset.snapTo(newOffset)
                            }
                        },
                        onDragEnd = {
                            when {
                                offset.value > 10f -> {
                                    scope.launch {
                                        offset.animateTo(-cardWidth) {
                                            onExpanded()
                                        }
                                    }
                                }

                                else -> {
                                    scope.launch {
                                        offset.animateTo(0f) {
                                            onCollapsed()
                                        }
                                    }
                                }
                            }
                        }
                    )
                },
            color = Color.Transparent,
            shadowElevation = 0.dp
        ) {
            TaskCard(
                taskIcon = getTaskIcon(task),
                blurColor = getTaskColor(task),
                priority = PriorityType.HIGH,
                taskTitle = task.title,
                taskDescription = task.description,
                taskCardType = TaskCardType.TASK,
                date = task.dueDate.toString()
            )
        }

    }
}

@Composable
fun getTaskIcon(task: Task): Painter {
    return painterResource(R.drawable.chef)
}

@Composable
fun getTaskColor(task: Task): Color {
    return Theme.color.pinkAccent.copy(alpha = .08f)
}

@Preview
@Composable
fun SwipableTaskPreview() {
    SwipableTask(
        task = Task(
            id = 1L,
            title = "Sample Task",
            description = "This is a sample task description.",
            taskPriority = TaskPriority.HIGH,
            status = TaskStatus.DONE,
            categoryId = 1L,
            dueDate = LocalDateTime(2025, 3, 12, 0, 0),
            createdAt = LocalDateTime(2025, 3, 1, 0, 0),
            updatedAt = LocalDateTime(2025, 3, 2, 0, 0)
        )
    )
}
