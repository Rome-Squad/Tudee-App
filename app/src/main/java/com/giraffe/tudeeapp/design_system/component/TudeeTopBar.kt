package com.giraffe.tudeeapp.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme

@Composable
fun TudeeTopBar(
    title: String,
    modifier: Modifier = Modifier,
    background: Color = Theme.color.surfaceHigh,
    titleColor: Color = Theme.color.title,
    iconRes: Int = R.drawable.ic_back,
    iconColor: Color = Theme.color.body,
    borderColor: Color = Theme.color.stroke
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(background)
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = CircleShape
                )
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = iconColor
            )

        }

        Text(
            text = title,
            color = titleColor,
            style = Theme.textStyle.title.large
        )

    }

}

@Preview(showBackground = true)
@Composable
fun TudeeTopBarPreview() {
    TudeeTopBar(
        modifier = Modifier
            .padding(16.dp),
        title = "Tasks"
    )
}