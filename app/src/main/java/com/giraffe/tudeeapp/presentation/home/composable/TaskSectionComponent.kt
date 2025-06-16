package com.giraffe.tudeeapp.presentation.home.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
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
import com.giraffe.tudeeapp.design_system.component.PriorityType
import com.giraffe.tudeeapp.design_system.component.TaskCard
import com.giraffe.tudeeapp.design_system.component.TaskCardType
import com.giraffe.tudeeapp.design_system.theme.Theme

@Composable
fun TaskSectionComponent(
    modifier: Modifier = Modifier,
    numberOfTasks: String = "12",
    taskStatus: String = "In Progress",
) {
    Column(modifier = modifier.height(266.dp)) {
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
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = 24.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(count = numberOfTasks.toInt()) {
                TaskCard(
                    modifier = Modifier.fillMaxWidth(),
                    taskIcon = painterResource(R.drawable.birthday_cake_icon),
                    blurColor = Theme.color.pinkAccent.copy(alpha = .08f),
                    priority = PriorityType.HIGH,
                    taskTitle = "Organize Study Desk",
                    taskDescription = "Review cell structure and functions for tomorrow...",
                    date = "12-03-2025",
                    taskCardType = TaskCardType.CATEGORY
                )
            }
        }
//        Box {
//            FabButton(
//                modifier = Modifier.align(Alignment.BottomEnd),
//                isLoading = false,
//                isDisable = false,
//                icon = painterResource(R.drawable.add_task_icon)
//            ) { }
//        }
    }
}

@Preview
@Composable
fun TaskSectionComponentPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
    ) {
        TaskSectionComponent()
    }
}