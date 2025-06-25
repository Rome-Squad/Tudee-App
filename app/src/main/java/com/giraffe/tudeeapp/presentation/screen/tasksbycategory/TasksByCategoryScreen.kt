package com.giraffe.tudeeapp.presentation.screen.tasksbycategory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.AlertBottomSheet
import com.giraffe.tudeeapp.design_system.component.CategoryBottomSheet
import com.giraffe.tudeeapp.design_system.component.DefaultSnackBar
import com.giraffe.tudeeapp.design_system.component.NoTasksSection
import com.giraffe.tudeeapp.design_system.component.TabsBar
import com.giraffe.tudeeapp.design_system.component.TaskCard
import com.giraffe.tudeeapp.design_system.component.TudeeTopBar
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme
import com.giraffe.tudeeapp.presentation.utils.EventListener
import com.giraffe.tudeeapp.presentation.utils.showErrorSnackbar
import com.giraffe.tudeeapp.presentation.utils.showSuccessSnackbar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TasksByCategoryScreen(
    viewModel: TasksByCategoryViewModel = koinViewModel(),
    onBackClick: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    TasksByCategoryContent(state, viewModel, viewModel.effect, onBackClick)
}

@Composable
fun TasksByCategoryContent(
    state: TasksByCategoryScreenState = TasksByCategoryScreenState(),
    actions: TasksByCategoryScreenInteractionListener,
    events: Flow<TasksByCategoryEffect>,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val snackState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    EventListener(events) { event ->
        when (event) {
            is TasksByCategoryEffect.CategoryDeleted -> {
                coroutineScope.launch {
                    snackState.showSuccessSnackbar(context.getString(R.string.category_deleted_successfully))
                    onBackClick()
                }
            }

            is TasksByCategoryEffect.CategoryEdited -> {
                coroutineScope.launch {
                    snackState.showSuccessSnackbar(context.getString(R.string.category_updated_successfully))
                }
            }

            is TasksByCategoryEffect.GetCategoryError -> {
                coroutineScope.launch {
                    snackState.showErrorSnackbar(context.getString(R.string.sorry_the_selected_category_could_not_be_found))
                }
            }

            is TasksByCategoryEffect.GetTasksError -> {
                coroutineScope.launch {
                    snackState.showErrorSnackbar(context.getString(R.string.failed_to_load_tasks))
                }
            }

            is TasksByCategoryEffect.DeleteCategoryError -> {
                coroutineScope.launch {
                    snackState.showErrorSnackbar(context.getString(R.string.unable_to_delete_the_category))
                }
            }

            is TasksByCategoryEffect.EditCategoryError -> {
                coroutineScope.launch {
                    snackState.showErrorSnackbar(context.getString(R.string.failed_to_update_the_category))
                }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surfaceHigh)
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        Column(modifier = Modifier.background(Theme.color.surface)) {
            TudeeTopBar(
                modifier = Modifier.background(Theme.color.surfaceHigh),
                title = state.selectedCategory?.name ?: "",
                withOption = state.selectedCategory?.isEditable == true,
                onClickBack = { onBackClick() },
                onClickEdit = { actions.setBottomSheetVisibility(true) }
            )
            TabsBar(
                tasks = state.tasks.mapValues { (_, value) -> value.size },
                onTabSelected = actions::selectTab
            )
            if (state.tasks[state.selectedTab].isNullOrEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    NoTasksSection()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxHeight(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    state.tasks[state.selectedTab]?.let { tasks ->
                        items(tasks) { task ->
                            TaskCard(task = task)
                        }
                    }
                }
            }
        }
        DefaultSnackBar(
            modifier = Modifier.align(Alignment.TopCenter),
            snackState = snackState,
        )
        CategoryBottomSheet(
            title = stringResource(R.string.edit_category),
            isVisible = state.isBottomSheetVisible,
            onVisibilityChange = actions::setBottomSheetVisibility,
            categoryToEdit = state.selectedCategory,
            onEditClick = actions::editCategory,
            onDeleteClick = { category ->
                actions.setAlertBottomSheetVisibility(true)
            }
        )
        AlertBottomSheet(
            title =  stringResource(R.string.delete_category),
            subTitle = stringResource(R.string.are_you_sure_to_continue),
            image = painterResource(R.drawable.sure_robot),
            positiveButtonTitle = stringResource(R.string.delete),
            negativeButtonTitle = stringResource(R.string.cancel),
            isVisible = state.isAlertBottomSheetVisible,
            onVisibilityChange = actions::setAlertBottomSheetVisibility,
            onRedBtnClick = { state.selectedCategory?.let { actions.deleteCategory(it) } },
            onBlueBtnClick = {
                actions.setAlertBottomSheetVisibility(false)
            }
        )
    }
}

@Preview()
@Composable
private fun Preview() {
    TudeeTheme {
        TasksByCategoryScreen()
    }
}