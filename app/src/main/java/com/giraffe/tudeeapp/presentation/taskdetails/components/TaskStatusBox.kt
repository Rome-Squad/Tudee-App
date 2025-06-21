package com.giraffe.tudeeapp.presentation.taskdetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.presentation.utils.toStringResource

@Composable
fun TaskStatusBox(
    modifier: Modifier = Modifier,
    status: TaskStatus = TaskStatus.TODO
) {

    var statusBackground = when (status) {
        TaskStatus.TODO -> Theme.color.yellowVariant
        TaskStatus.IN_PROGRESS -> Theme.color.purpleVariant
        TaskStatus.DONE -> Theme.color.greenVariant
    }
    var contentColor = when (status) {
        TaskStatus.TODO -> Theme.color.yellowAccent
        TaskStatus.IN_PROGRESS -> Theme.color.purpleAccent
        TaskStatus.DONE -> Theme.color.greenAccent
    }
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(statusBackground)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(5.dp)
                .clip(CircleShape)
                .background(Theme.color.purpleAccent)
        )

        Text(
            text = status.toStringResource(),
            style = Theme.textStyle.body.small,
            color = Theme.color.purpleAccent
        )

    }
}