package com.giraffe.tudeeapp.presentation.categories

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.CategoryBottomSheet
import com.giraffe.tudeeapp.design_system.component.CategoryItem
import com.giraffe.tudeeapp.design_system.component.HeaderContent
import com.giraffe.tudeeapp.design_system.component.TudeeSnackBar
import com.giraffe.tudeeapp.design_system.component.button_type.FabButton
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme
import com.giraffe.tudeeapp.presentation.navigation.Screen
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
                        is CategoriesScreenEvents.NavigateToTasksByCategoryScreen -> {
                            navController.navigate("${Screen.TasksByCategoryScreen.route}/${event.categoryId}")
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
    state: CategoriesScreenState,
    actions: CategoriesScreenActions
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Theme.color.surface)
    ) {
        Column {
            HeaderContent("Categories")
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize(),
                contentPadding = PaddingValues(top = 12.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.categories.size) { index ->
                    CategoryItem(
                        icon = rememberAsyncImagePainter(
                            ImageRequest
                                .Builder(LocalContext.current)
                                .data(data = state.categories[index].imageUri)
                                .build()
                        ),
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
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 8.dp)
        )

        if (state.isBottomSheetVisible) {
            CategoryBottomSheet(
                title = "Add new category",
                onVisibilityChange = actions::setBottomSheetVisibility,
                onAddClick = actions::addCategory,
            )
        }

        AnimatedVisibility(state.showSuccessSnackBar) {
            TudeeSnackBar(
                message = if (state.error == null) "Added category successfully." else "Some error happened",
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
private fun CategoriesScreenPreview() {
    TudeeTheme {
        CategoriesScreen(navController = NavController(LocalContext.current))
    }
}
