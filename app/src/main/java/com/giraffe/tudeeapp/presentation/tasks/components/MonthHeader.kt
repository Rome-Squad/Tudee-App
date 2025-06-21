package com.giraffe.tudeeapp.presentation.tasks.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme

@Composable
fun MonthHeader(
    monthYearLabel: String,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onMonthClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val layoutDirection = LocalLayoutDirection.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .scale(scaleX = if (layoutDirection == LayoutDirection.Rtl) -1f else 1f, scaleY = 1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .border(
                    width = 1.dp,
                    color = Theme.color.stroke,
                    shape = CircleShape
                )
                .clickable(onClick = onPreviousClick),
            contentAlignment = Alignment.Center
        ){
            Icon(
                modifier = Modifier.scale(scaleX = if (layoutDirection == LayoutDirection.Rtl) -1f else 1f, scaleY = 1f),
                painter = painterResource(R.drawable.leftarrow),
                contentDescription = "Previous Month",
                tint = Theme.color.body
            )
        }

        Row(
            modifier = Modifier
                .clickable(onClick = onMonthClick)
                .scale(scaleX = if (layoutDirection == LayoutDirection.Rtl) -1f else 1f, scaleY = 1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = monthYearLabel,
                style = Theme.textStyle.label.medium,
                color = Theme.color.body
            )
            Icon(
                modifier= Modifier.padding(start = 4.dp),
                painter = painterResource(R.drawable.droparrow),
                contentDescription = "Select Date",
                tint = Theme.color.body
            )
        }

        Box(
            modifier = Modifier
                .size(32.dp)
                .border(
                    width = 1.dp,
                    color = Theme.color.stroke,
                    shape = CircleShape
                )
                .clickable(onClick = onNextClick),
            contentAlignment = Alignment.Center
        ){
            Icon(
                modifier = Modifier.scale(scaleX = if (layoutDirection == LayoutDirection.Rtl) -1f else 1f, scaleY = 1f),
                painter = painterResource(R.drawable.rightarrow),
                contentDescription = "Next Month",
                tint = Theme.color.body
            )
        }
    }
}