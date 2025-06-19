package com.giraffe.tudeeapp.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.domain.model.task.TaskStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabsBar(
    modifier: Modifier = Modifier,
    startTab : TaskStatus = TaskStatus.IN_PROGRESS,
    onTabSelected: (TaskStatus) -> Unit = {},
    tasks: Map<TaskStatus, Int> = mapOf()
) {
    var selectedTab by rememberSaveable { mutableIntStateOf(startTab.ordinal) }
    PrimaryTabRow(
        modifier = modifier,
        containerColor = Theme.color.surfaceHigh,
        selectedTabIndex = selectedTab,
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier.tabIndicatorOffset(selectedTab, matchContentSize = false),
                width = Dp.Unspecified,
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                color = Theme.color.secondary
            )
        }
    ) {
        tasks.forEach { tab ->
            val title = when (tab.key) {
                TaskStatus.TODO -> "To Do"
                TaskStatus.IN_PROGRESS -> "In progress"
                TaskStatus.DONE -> "Done"
            }
            val index = tab.key.ordinal
            Tab(
                selected = selectedTab == index,
                onClick = {
                    selectedTab = index
                    onTabSelected(tab.key)
                },
                selectedContentColor = Theme.color.title,
                unselectedContentColor = Theme.color.hint,
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .weight(.73f),
                            textAlign = TextAlign.Center,
                            text = title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = if (selectedTab == index) Theme.textStyle.label.medium else Theme.textStyle.label.small,
                        )

                        if (tab.value != 0 && selectedTab == index) {
                            Box(
                                modifier = Modifier
                                    .weight(.27f)
                                    .size(28.dp)
                                    .aspectRatio(1f)
                                    .background(color = Theme.color.surface, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = tab.value.toString(),
                                    style = Theme.textStyle.label.medium,
                                    color = Theme.color.body
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    TabsBar()
}