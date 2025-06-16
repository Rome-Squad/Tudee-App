package com.giraffe.tudeeapp.presentation.categories.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.CategoryItem
import com.giraffe.tudeeapp.design_system.component.HeaderContent
import com.giraffe.tudeeapp.design_system.component.TudeeSnackBar
import com.giraffe.tudeeapp.design_system.component.button_type.FabButton
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.presentation.categories.CategoriesScreenAction
import com.giraffe.tudeeapp.presentation.categories.CategoryViewModel
import com.giraffe.tudeeapp.presentation.categories.uiEvent.CategoriesUiEvent
import com.giraffe.tudeeapp.presentation.categories.uistates.CategoriesScreenUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriesScreen(viewModel: CategoryViewModel = koinViewModel(), navController: NavController) {
    val state = viewModel.categoriesUiState.collectAsState().value
    val lifeCycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifeCycleOwner.lifecycle) {
        lifeCycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                viewModel.events.collect { event ->
                    when (event) {
                        is CategoriesUiEvent.NavigateToTasksByCategoryScreen -> {

                        }
                    }
                }
            }
        }
    }

    CategoriesContent(state, viewModel)
}

@Composable
fun CategoriesContent(
    state: CategoriesScreenUiState,
    actions: CategoriesScreenAction
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Theme.color.surface)
    ) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            HeaderContent("Categories")
            LazyVerticalGrid(
                columns = GridCells.Adaptive(360.dp),
                contentPadding = PaddingValues(
                    top = 12.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 69.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                items(state.categories.size) { index ->
                    CategoryItem(
                        icon = painterResource(state.categories[index].image),
                        categoryName = state.categories[index].name,
                        count = state.categories[index].taskCount,
                        onClick = {
                            actions.selectCategory(state.categories[index].id)
                        }
                    )
                }
            }
        }

        FabButton(
            icon = painterResource(R.drawable.add_category_icon),
            onClick = { actions.setBottomSheetVisibility(isVisible = true) },
            modifier = Modifier.align(Alignment.BottomEnd)
        )

        if (state.isBottomSheetVisible) {
        }

        AnimatedVisibility(state.showSuccessSnackBar) {
            TudeeSnackBar(
                message = "Added category successfully.",
                iconRes = R.drawable.ic_success,
                iconTintColor = Theme.color.greenAccent,
                iconBackgroundColor = Theme.color.greenVariant,
                modifier = Modifier.padding(16.dp)
            )
        }

    }

}

