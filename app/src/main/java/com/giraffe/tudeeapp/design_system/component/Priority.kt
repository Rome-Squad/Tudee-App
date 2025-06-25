package com.giraffe.tudeeapp.design_system.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme
import com.giraffe.tudeeapp.domain.entity.task.TaskPriority
import com.giraffe.tudeeapp.presentation.utils.toStringResource


@Composable
fun Priority(
    icon: Painter,
    label: String,
    selectedBackgroundColor: Color,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) selectedBackgroundColor else Theme.color.surfaceLow,
        animationSpec = tween(durationMillis = 300),
    )
    val contentColor by animateColorAsState(
        targetValue = if (isSelected) Theme.color.onPrimary else Theme.color.hint,
        animationSpec = tween(durationMillis = 300),
    )

    LabelIconBox(
        icon = icon,
        label = label,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun PriorityPreview() {
    TudeeTheme {
        Priority(
            icon = painterResource(R.drawable.trade_down_icon),
            label = TaskPriority.LOW.toStringResource(),
            selectedBackgroundColor = Theme.color.greenAccent,
            isSelected = true
        )
    }
}