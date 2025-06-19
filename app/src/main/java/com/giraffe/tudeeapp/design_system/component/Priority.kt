package com.giraffe.tudeeapp.design_system.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.domain.model.task.TaskPriority


@Composable
fun Priority(
    priorityType: TaskPriority,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {

    var label: String = when (priorityType) {
        TaskPriority.HIGH -> stringResource(R.string.high)
        TaskPriority.MEDIUM -> stringResource(R.string.medium)
        TaskPriority.LOW -> stringResource(R.string.low)
    }
    var icon: Int = when (priorityType) {
        TaskPriority.HIGH -> R.drawable.flag_icon
        TaskPriority.MEDIUM ->R.drawable.alert_icon
        TaskPriority.LOW -> R.drawable.trade_down_icon
    }
    val backgroundColor = when (priorityType) {
        TaskPriority.HIGH -> Theme.color.pinkAccent
        TaskPriority.MEDIUM -> Theme.color.yellowAccent
        TaskPriority.LOW -> Theme.color.greenAccent
    }

    LabelIconBox(
        icon = painterResource(icon),
        label = label,
        backgroundColor = if (isSelected) backgroundColor else Theme.color.surfaceLow,
        contentColor = if (isSelected) Theme.color.onPrimary else Theme.color.hint,
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