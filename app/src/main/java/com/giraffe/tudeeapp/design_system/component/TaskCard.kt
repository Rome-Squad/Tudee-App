package com.giraffe.tudeeapp.design_system.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.domain.entity.task.Task
import com.giraffe.tudeeapp.presentation.utils.emptyTask

@Composable
fun TaskCard(
    modifier: Modifier = Modifier,
    task: Task,
    isDateVisible: Boolean = true,
) {
    val blurColor = getColorForCategoryIcon(task.category.name).copy(alpha = .08f)
    Column(
        modifier
            .background(color = Theme.color.surfaceHigh, shape = RoundedCornerShape(16.dp))
            .padding(
                top = 4.dp, start = 4.dp, end = 12.dp, bottom = 12.dp
            ),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                Modifier
                    .size(56.dp)
                    .graphicsLayer {
                        spotShadowColor = blurColor
                        ambientShadowColor = blurColor
                        shadowElevation = with(density) { 50.dp.toPx() }
                    },
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest
                            .Builder(LocalContext.current)
                            .data(data = task.category.imageUri)
                            .build()
                    ),
                    contentDescription = stringResource(R.string.task_icon),
                    Modifier
                        .size(32.dp)
                        .align(Alignment.Center), contentScale = ContentScale.Crop
                )
            }
            Row(
                Modifier.align(Alignment.CenterVertically),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (isDateVisible) {
                    LabelIconBox(
                        backgroundColor = Theme.color.surface,
                        contentColor = Theme.color.body,
                        icon = painterResource(R.drawable.calendar_icon),
                        label = task.dueDate.toString()
                    )
                }
                Priority(
                    priorityType = task.taskPriority,
                    isSelected = true
                )
            }


        }
        Text(
            text = task.title,
            style = Theme.textStyle.label.large,
            color = Theme.color.body,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 8.dp, end = 12.dp)
        )
        Text(
            text = task.description,
            style = Theme.textStyle.label.small,
            color = Theme.color.hint,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 8.dp, end = 12.dp)
        )
    }
}

@Composable
fun getColorForCategoryIcon(categoryName: String): Color {
    return when (categoryName) {
        "Education" -> Theme.color.purpleAccent
        "Adoration" -> Theme.color.primary
        "Family & friend" -> Theme.color.secondary
        "Cooking" -> Theme.color.pinkAccent
        "Traveling" -> Theme.color.yellowAccent
        "Coding" -> Theme.color.purpleAccent
        "Fixing bugs" -> Theme.color.pinkAccent
        "Medical" -> Theme.color.primary
        "Shopping" -> Theme.color.secondary
        "Agriculture" -> Theme.color.greenAccent
        "Entertainment" -> Theme.color.yellowAccent
        "Gym" -> Theme.color.primary
        "Cleaning" -> Theme.color.greenAccent
        "Work" -> Theme.color.secondary
        "Event" -> Theme.color.pinkAccent
        "Budgeting" -> Theme.color.purpleAccent
        "Self-care" -> Theme.color.yellowAccent
        else -> Theme.color.purpleAccent
    }
}

@Preview(showBackground = true)
@Composable
fun TaskCardPreview() {
    Column {
        TaskCard(task = emptyTask())
    }
}