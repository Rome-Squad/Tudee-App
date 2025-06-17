package com.giraffe.tudeeapp.presentation.categories.tasks_by_category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.design_system.component.TabsBar
import com.giraffe.tudeeapp.design_system.component.TudeeTopBar
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme
import com.giraffe.tudeeapp.presentation.categories.CategoryBottomSheet
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TasksByCategoryScreen(
    viewModel: TasksByCategoryViewModel = koinViewModel(),
    //navController: NavController
) {
    val state by viewModel.state.collectAsState()
    TasksByCategoryContent(state, viewModel)
}

@Composable
fun TasksByCategoryContent(
    state: TasksByCategoryScreenState = TasksByCategoryScreenState(),
    actions: TasksByCategoryScreenActions,
    //navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surfaceHigh),
    ) {

        TudeeTopBar(
            title = state.selectedCategoryStr,
            withOption = true,
            onClickBack = { /*navController.popBackStack()*/ },
            onClickEdit = { actions.setBottomSheetVisibility(true) }
        )
        TabsBar(onTabSelected = actions::selectTab)
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .background(Theme.color.surface),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.tasks[state.selectedTab]?.let { tasks ->
                items(tasks) { task ->
                    /*TaskCard(
                        taskIcon = rememberAsyncImagePainter(
                            ImageRequest
                                .Builder(LocalContext.current)
                                .data(data = task.ca)
                                .build()
                        ),
                        blurColor = getColorForCategoryIcon("Education"),
                        priority = PriorityType.MEDIUM,
                        taskTitle = "Organize Study Desk",
                        date = "12-03-2025",
                        taskDescription = "Review cell structure and functions for tomorrow...",
                        taskCardType = TaskCardType.CATEGORY
                    )*/
                }
            }

        }

        if (state.isBottomSheetVisible) {
            CategoryBottomSheet(
                title = "Edit category",
                onVisibilityChange = actions::setBottomSheetVisibility,
                categoryToEdit = state.selectedCategory,
                onEditClick = actions::editCategory
            )
        }

    }
}

@Preview()
@Composable
private fun Preview() {
    TudeeTheme {
        TasksByCategoryScreen()
    }
}