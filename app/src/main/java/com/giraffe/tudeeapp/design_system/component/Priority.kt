package com.giraffe.tudeeapp.design_system.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.domain.model.task.TaskPriority
import com.giraffe.tudeeapp.presentation.utils.toStringResource


@Composable
fun Priority(
    priorityType: TaskPriority,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    var icon = when (priorityType) {
        TaskPriority.HIGH -> R.drawable.flag_icon
        TaskPriority.MEDIUM -> R.drawable.alert_icon
        TaskPriority.LOW -> R.drawable.trade_down_icon
    }
    val selectedBackgroundColor = when (priorityType) {
        TaskPriority.HIGH -> Theme.color.pinkAccent
        TaskPriority.MEDIUM -> Theme.color.yellowAccent
        TaskPriority.LOW -> Theme.color.greenAccent
    }
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) selectedBackgroundColor else Theme.color.surfaceLow,
        animationSpec = tween(durationMillis = 300),
    )
    val contentColor by animateColorAsState(
        targetValue = if (isSelected) Theme.color.onPrimary else Theme.color.hint,
        animationSpec = tween(durationMillis = 300),
    )

    LabelIconBox(
        icon = painterResource(icon),
        label = priorityType.toStringResource(),
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun PriorityPreview() {
    Priority(
        priorityType = TaskPriority.LOW,
        isSelected = true
    )
}