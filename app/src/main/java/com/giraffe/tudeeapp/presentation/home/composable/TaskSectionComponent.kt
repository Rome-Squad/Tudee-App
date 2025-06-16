package com.giraffe.tudeeapp.presentation.home.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme

@Composable
fun TaskSectionComponent(
    modifier: Modifier = Modifier,
    numberOfTasks: String = "12",
    taskStatus: String = "In Progress",
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = taskStatus,
                color = Theme.color.title,
                style = Theme.textStyle.title.large
            )
            Box(
                modifier = Modifier.background(
                    color = Theme.color.surfaceHigh,
                    shape = RoundedCornerShape(100.dp)
                )
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = numberOfTasks,
                        color = Theme.color.body,
                        style = Theme.textStyle.label.small
                    )
                    Icon(
                        painter = painterResource(R.drawable.arrow_icon),
                        contentDescription = "arrow icon"
                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun TaskSectionComponentPreview() {
    Box(modifier = Modifier.background(Color.White)) {
        TaskSectionComponent()
    }
}