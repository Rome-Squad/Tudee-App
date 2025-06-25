package com.giraffe.tudeeapp.design_system.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.design_system.theme.Theme

@Composable
fun DayCard(
    dayNumber: String,
    dayName: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean = true,
    onClick: () -> Unit
) {
    val dayNumberColor by animateColorAsState(
        targetValue = if (isSelected) Theme.color.onPrimary else Theme.color.body
    )
    val dayNameColor by animateColorAsState(
        targetValue = if (isSelected) Theme.color.onPrimaryCaption else Theme.color.hint
    )
    val firstBackgroundColor by animateColorAsState(
        if (isSelected) Theme.color.primaryGradient.startGradient else Theme.color.surface
    )
    val secondBackgroundColor by animateColorAsState(
        if (isSelected) Theme.color.primaryGradient.endGradient else Theme.color.surface
    )
    Column(
        modifier = modifier
            .width(56.dp)
            .background(
                brush = Brush.verticalGradient(listOf(firstBackgroundColor, secondBackgroundColor)),
                shape = RoundedCornerShape(16.dp)
            )
            .clip(shape = RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = dayNumber,
            style = Theme.textStyle.title.medium,
            color = dayNumberColor
        )
        Text(
            text = dayName,
            style = Theme.textStyle.body.small,
            color = dayNameColor
        )
    }
}

@Preview
@Composable
private fun Preview() {
    DayCard(
        dayNumber = "15",
        dayName = "Mon",
        onClick = {}
    )
}