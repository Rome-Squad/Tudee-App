package com.giraffe.tudeeapp.presentation.tasks_by_category

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.AlertBottomSheet
import com.giraffe.tudeeapp.design_system.component.CategoryBottomSheet
import com.giraffe.tudeeapp.design_system.component.NoTasksSection
import com.giraffe.tudeeapp.design_system.component.TabsBar
import com.giraffe.tudeeapp.design_system.component.TaskCard
import com.giraffe.tudeeapp.design_system.component.TaskCardType
import com.giraffe.tudeeapp.design_system.component.TudeeSnackBar
import com.giraffe.tudeeapp.design_system.component.TudeeTopBar
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme
import com.giraffe.tudeeapp.presentation.utils.EventListener
import com.giraffe.tudeeapp.presentation.utils.errorToMessage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TasksByCategoryScreen(
    viewModel: TasksByCategoryViewModel = koinViewModel(),
    onBackClick: () -> Unit = {},
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    EventListener(viewModel.events) { event ->
        when (event) {
            is TasksByCategoryEvents.CategoryDeleted -> {
                viewModel.showSuccessMsg(context.getString(R.string.category_deleted_successfully))
                onBackClick()
            }

            is TasksByCategoryEvents.CategoryEdited -> {
                viewModel.showSuccessMsg(context.getString(R.string.category_updated_successfully))
            }
        }
    }
    TasksByCategoryContent(state, viewModel, onBackClick)
}

@Composable
fun TasksByCategoryContent(
    state: TasksByCategoryScreenState = TasksByCategoryScreenState(),
    actions: TasksByCategoryScreenActions,
    onBackClick: () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = Theme.color.surfaceHigh,
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surfaceHigh),
    ) {
        Column {
            TudeeTopBar(
                title = state.selectedCategory?.name ?: "",
                withOption = state.selectedCategory?.isEditable == true,
                onClickBack = { onBackClick() },
                onClickEdit = { actions.setBottomSheetVisibility(true) }
            )
            TabsBar(
                tasks = state.tasks.mapValues { (_, value) -> value.size },
                onTabSelected = actions::selectTab
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(Theme.color.surface),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                state.tasks[state.selectedTab]?.let { tasks ->
                    if (tasks.isNotEmpty()) {
                        items(tasks) { task ->
                            TaskCard(
                                taskIcon = rememberAsyncImagePainter(
                                    ImageRequest
                                        .Builder(LocalContext.current)
                                        .data(data = state.selectedCategory?.imageUri)
                                        .build()
                                ),
                                categoryName = state.selectedCategory?.name ?: "",
                                priority = task.priorityType,
                                taskTitle = task.title,
                                date = task.createdAt.toString(),
                                taskDescription = task.description,
                                taskCardType = TaskCardType.CATEGORY
                            )
                        }
                    } else {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillParentMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                NoTasksSection()
                            }
                        }
                    }
                }

            }

            if (state.isBottomSheetVisible) {
                CategoryBottomSheet(
                    title = stringResource(R.string.edit_category),
                    onVisibilityChange = actions::setBottomSheetVisibility,
                    categoryToEdit = state.selectedCategory,
                    onEditClick = actions::editCategory,
                    onDeleteClick = { category ->
                        actions.setCategoryToDelete(category)
                        actions.setBottomSheetVisibility(false)
                        actions.setAlertBottomSheetVisibility(true)
                    }
                )
            }

            if (state.isAlertBottomSheetVisible) {
                AlertBottomSheet(
                    onRedBtnClick = { state.categoryToDelete?.let { actions.deleteCategory(it) } },
                    onBlueBtnClick = {
                        actions.setCategoryToDelete(null)
                        actions.setAlertBottomSheetVisibility(false)
                    }
                )
            }

        }
        AnimatedVisibility(state.successMsg != null || state.error != null) {
            TudeeSnackBar(
                message = state.successMsg ?: errorToMessage(state.error!!),
                iconRes = if (state.error == null) R.drawable.ic_success else R.drawable.ic_error,
                iconTintColor = if (state.error == null) Theme.color.greenAccent else Theme.color.error,
                iconBackgroundColor = if (state.error == null) Theme.color.greenVariant else Theme.color.errorVariant,
                modifier = Modifier.padding(16.dp)
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