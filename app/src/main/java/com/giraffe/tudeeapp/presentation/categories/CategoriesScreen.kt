package com.giraffe.tudeeapp.presentation.categories

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.CategoryBottomSheet
import com.giraffe.tudeeapp.design_system.component.CategoryItem
import com.giraffe.tudeeapp.design_system.component.HeaderContent
import com.giraffe.tudeeapp.design_system.component.TudeeSnackBar
import com.giraffe.tudeeapp.design_system.component.button_type.FabButton
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme
import com.giraffe.tudeeapp.presentation.utils.EventListener
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriesScreen(
    viewModel: CategoryViewModel = koinViewModel(),
    navigateToTaskByCategoryScreen: (categoryId: Long) -> Unit = {}
) {

    val state = viewModel.categoriesUiState.collectAsState().value
    EventListener(viewModel.events) { event ->
        when (event) {
            is CategoriesScreenEvents.NavigateToTasksByCategoryScreen -> {
                navigateToTaskByCategoryScreen(event.categoryId)
            }
        }
    }
    CategoriesContent(state, viewModel)
}

@Composable
fun CategoriesContent(
    state: CategoriesScreenState,
    actions: CategoriesScreenActions,
) {
    val statusBarHeightDp: Dp = with(LocalDensity.current) {
        WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(Theme.color.surfaceHigh)
            .padding(top = statusBarHeightDp)
    ) {
        Column {
            HeaderContent(stringResource(R.string.categories))
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 104.dp),
                modifier = Modifier
                    .background(Theme.color.surface)
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
                title = stringResource(R.string.add_new_category),
                onVisibilityChange = actions::setBottomSheetVisibility,
                onAddClick = actions::addCategory,
                isVisible = true
            )
        }

        AnimatedVisibility(state.isSnackBarVisible) {
            TudeeSnackBar(
                message = (if (state.error == null) stringResource(R.string.added_category_successfully) else stringResource(
                    R.string.some_error_happened
                )),
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
        CategoriesScreen()
    }
}
