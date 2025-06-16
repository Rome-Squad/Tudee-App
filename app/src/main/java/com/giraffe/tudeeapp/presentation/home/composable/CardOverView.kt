package com.giraffe.tudeeapp.presentation.home.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme

@Composable
fun CardOverView(
    modifier: Modifier = Modifier,
    color: Color,
    icon: Painter,
    taskCount: String,
    taskStatus: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .height(112.dp)
            .background(color)
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(38.dp),
            painter = painterResource(id = R.drawable.overview_card_background),
            tint = Theme.color.onPrimary,
            contentDescription = "calendar_icon",
        )
        Column(
            modifier = Modifier
                .padding(start = 12.dp, top = 12.dp),
        ) {
            Icon(
                modifier = Modifier
                    .size(38.dp),
                painter = icon,
                tint = Theme.color.onPrimary,
                contentDescription = "card overview icon",
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = taskCount,
                color = Theme.color.onPrimary,
                style = Theme.textStyle.headline.medium
            )
            Text(
                text = taskStatus,
                color = Theme.color.onPrimaryCaption,
                style = Theme.textStyle.label.small
            )
        }
    }
}