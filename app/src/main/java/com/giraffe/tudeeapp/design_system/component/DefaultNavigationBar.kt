package com.giraffe.tudeeapp.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    onNavigation: (String) -> Unit = {},
) {
    val items = listOf(
        BottomNavigationItem(
            route = Screen.HomeScreen.route,
            selectedIcon = R.drawable.home_selected,
            unselectedIcon = R.drawable.home_unselected
        ),
        BottomNavigationItem(
            route = "${Screen.TaskScreen.route}/${0}",
            selectedIcon = R.drawable.task_selected,
            unselectedIcon = R.drawable.tasks_unselected
        ),
        BottomNavigationItem(
            route = Screen.CategoriesScreen.route,
            selectedIcon = R.drawable.categories_selected,
            unselectedIcon = R.drawable.categories_unselected
        )
    )
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    CompositionLocalProvider(LocalRippleConfiguration provides null) {
        NavigationBar(
            modifier = modifier,
            containerColor = Theme.color.surfaceHigh
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = currentRoute?.contains(item.route.split("/").first()) == true
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        onNavigation(item.route)
                        if (!isSelected) {
                            navController.navigate(item.route)
                        }
                    },
                    icon = {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) Theme.color.primaryVariant else Theme.color.surfaceHigh),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(
                                    if (isSelected) item.selectedIcon else item.unselectedIcon
                                ),
                                tint = if (isSelected) Theme.color.primary else Theme.color.hint.copy(
                                    .38f
                                ),
                                contentDescription = item.route
                            )
                        }
                    },
                    colors = NavigationBarItemDefaults.colors().copy(
                        selectedIndicatorColor = Color.Transparent
                    ),
                )
            }
        }
    }
}

data class BottomNavigationItem(
    val route: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
)

@Preview
@Composable
fun NavBarPreview() {
    DefaultNavigationBar(navController = rememberNavController())
}
