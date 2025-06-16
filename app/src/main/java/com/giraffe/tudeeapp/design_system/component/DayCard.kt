package com.giraffe.tudeeapp.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.design_system.theme.Theme

@Composable
fun DayCard(
    modifier: Modifier = Modifier,
    dayNumber: Int = 15,
    dayName: String = "Mon",
    isSelected: Boolean = true
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
            .background(
            brush = Brush.verticalGradient(backgroundColor),
            shape = RoundedCornerShape(16.dp)
        ).padding(horizontal = 14.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier,
            text = dayNumber.toString(),
            style = Theme.textStyle.title.medium,
            color = dayNumberColor
        )
        Text(
            modifier = Modifier,
            text = dayName,
            style = Theme.textStyle.body.small,
            color = dayNameColor
        )
    }
}

@Preview
@Composable
private fun Preview() {
    DayCard()
}