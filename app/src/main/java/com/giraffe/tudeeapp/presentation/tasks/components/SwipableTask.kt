package com.giraffe.tudeeapp.presentation.tasks.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.giraffe.tudeeapp.design_system.component.TaskCard
import com.giraffe.tudeeapp.design_system.component.TaskCardType
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.presentation.uimodel.TaskUi
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@Composable
fun SwipableTask(
    taskUi: TaskUi,
    modifier: Modifier = Modifier,
    isRevealed: Boolean = false,
    onExpanded: () -> Unit = {},
    onCollapsed: () -> Unit = {},
    action: () -> Unit = { }
) {

    val layoutDirection = LocalLayoutDirection.current
    val buttonWidth = with(LocalDensity.current) { 76.dp.toPx() }
    val offset = remember {
        Animatable(initialValue = 0f)
    }
    val scope = rememberCoroutineScope()

    // Animate the initial offset based on the isRevealed state
    LaunchedEffect(key1 = isRevealed, buttonWidth) {
        if (isRevealed) {
            offset.animateTo(-buttonWidth)
        } else {
            offset.animateTo(0f)
        }
    }

    Box(
        modifier = modifier
            .height(125.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .padding(1.dp)
                .fillMaxWidth()
                .background(color = Theme.color.errorVariant, shape = RoundedCornerShape(16.dp))
        ) {
            TaskDeleteButton(
                onClick = action,
                modifier = Modifier
                    .fillMaxHeight()
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
                                val correctedDragAmount = if (layoutDirection == LayoutDirection.Ltr) dragAmount else -dragAmount
                                val newOffset =
                                    (offset.value + correctedDragAmount).coerceIn(-buttonWidth, 0f)
                                offset.snapTo(newOffset)
                            }
                        },
                        onDragEnd = {
                            when {
                                offset.value > -buttonWidth / 2 -> {
                                    scope.launch {
                                        offset.animateTo(0f) {
                                            onExpanded()
                                        }
                                    }
                                }

                                else -> {
                                    scope.launch {
                                        offset.animateTo(-buttonWidth) {
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
                taskIcon = rememberAsyncImagePainter(
                    ImageRequest
                        .Builder(LocalContext.current)
                        .data(data = taskUi.category.imageUri)
                        .build()
                ),
                priority = taskUi.priorityType,
                taskTitle = taskUi.title,
                taskDescription = taskUi.description,
                taskCardType = TaskCardType.TASK,
                date = null
            )
        }
    }
}