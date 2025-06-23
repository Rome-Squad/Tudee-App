package com.giraffe.tudeeapp.presentation.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.CategoryBottomSheet
import com.giraffe.tudeeapp.design_system.component.CategoryItem
import com.giraffe.tudeeapp.design_system.component.DefaultSnackBar
import com.giraffe.tudeeapp.design_system.component.HeaderContent
import com.giraffe.tudeeapp.design_system.component.button_type.FabButton
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme
import com.giraffe.tudeeapp.presentation.utils.EventListener
import com.giraffe.tudeeapp.presentation.utils.errorToMessage
import com.giraffe.tudeeapp.presentation.utils.showErrorSnackbar
import com.giraffe.tudeeapp.presentation.utils.showSuccessSnackbar
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriesScreen(
    viewModel: CategoryViewModel = koinViewModel(),
    navigateToTaskByCategoryScreen: (categoryId: Long) -> Unit = {}
) {

    val state = viewModel.categoriesUiState.collectAsState().value
    val snackState = remember { SnackbarHostState() }
    val context = LocalContext.current

    EventListener(viewModel.events) { event ->
        when (event) {
            is CategoriesScreenEvents.NavigateToTasksByCategoryScreen -> {
                navigateToTaskByCategoryScreen(event.categoryId)
            }

            is CategoriesScreenEvents.CategoryAdded -> {
                snackState.showSuccessSnackbar(context.getString(R.string.added_category_successfully))
            }

            is CategoriesScreenEvents.Error -> {
                snackState.showErrorSnackbar(
                    context.errorToMessage(event.error)
                )
            }
        }
    }
    CategoriesContent(
        state = state,
        actions = viewModel,
        snackState = snackState
    )
}

@Composable
fun CategoriesContent(
    state: CategoriesScreenState,
    actions: CategoriesScreenActions,
    snackState: SnackbarHostState
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Theme.color.surfaceHigh)
            .systemBarsPadding()
    ) {
        Column {
            HeaderContent(stringResource(R.string.categories))
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 104.dp),
                modifier = Modifier
                    .background(Theme.color.surface)
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
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
                .padding(end = 12.dp, bottom = 12.dp)
        )

        CategoryBottomSheet(
            isVisible = state.isBottomSheetVisible,
            title = stringResource(R.string.add_new_category),
            onVisibilityChange = actions::setBottomSheetVisibility,
            onAddClick = actions::addCategory,
        )
        DefaultSnackBar(snackState = snackState)
    }

}

@Preview()
@Composable
private fun CategoriesScreenPreview() {
    TudeeTheme {
        CategoriesScreen()
    }
}
