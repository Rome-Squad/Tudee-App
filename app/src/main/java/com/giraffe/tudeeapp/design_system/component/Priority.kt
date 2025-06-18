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
    var icon: Int
    var label: String
    when (priorityType) {
        TaskPriority.HIGH -> {
            icon = R.drawable.flag_icon; label = stringResource(R.string.high)
        }

        TaskPriority.MEDIUM -> {
            icon = R.drawable.alert_icon; label = stringResource(R.string.medium)
        }

        TaskPriority.LOW -> {
            icon = R.drawable.trade_down_icon; label = stringResource(R.string.low)
        }
    }

    LabelIconBox(
        icon = painterResource(icon),
        label = label,
        backgroundColor = if (isSelected) Theme.color.pinkAccent else Theme.color.surfaceLow,
        contentColor = if (isSelected) Theme.color.onPrimary else Theme.color.hint,
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun PriorityPreview() {
    Priority(
        priorityType = TaskPriority.HIGH,
        isSelected = true
    )
}