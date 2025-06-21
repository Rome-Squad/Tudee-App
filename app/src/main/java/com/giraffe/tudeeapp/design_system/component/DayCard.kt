package com.giraffe.tudeeapp.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.design_system.theme.Theme

@Composable
fun DayCard(
    modifier: Modifier = Modifier,
    dayNumber: Int = 15,
    dayName: String = "Mon",
    isSelected: Boolean = true,
    onClick: () -> Unit
) {
    val dayNumberColor = if (isSelected) Theme.color.onPrimary else Theme.color.body
    val dayNameColor = if (isSelected) Theme.color.onPrimaryCaption else Theme.color.hint
    val backgroundColor = if (isSelected) listOf(
        Theme.color.primaryGradient.startGradient,
        Theme.color.primaryGradient.endGradient,
    ) else listOf(
        Theme.color.surface,
        Theme.color.surface,
    )
    Column(
        modifier = modifier
            .defaultMinSize(minWidth = 56.dp, minHeight = 65.dp)
            .background(
                brush = Brush.verticalGradient(backgroundColor),
                shape = RoundedCornerShape(16.dp)
            )
            .clip(shape = RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(start = 17.dp, end = 17.dp, top = 12.dp),
            text = dayNumber.toString(),
            style = Theme.textStyle.title.medium,
            color = dayNumberColor
        )
        Text(
            modifier = Modifier.padding(start = 14.dp, end = 14.dp, bottom = 12.dp),
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
        onClick = {}
    )
}