package com.giraffe.tudeeapp.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.presentation.navigation.Screen
import com.giraffe.tudeeapp.design_system.theme.Theme

@Composable
fun NavBar(modifier: Modifier = Modifier, navController: NavController) {
    val selectedItem = rememberSaveable {
        mutableIntStateOf(0)
    }

    val navigationItems = listOf(
        NavigationItem(
            route = Screen.HomeScreen.route,
            iconSelected = R.drawable.home_selected,
            iconUnSelected = R.drawable.home_unselected
        ),
        NavigationItem(
            route = "${Screen.TaskScreen.route}/${0}",
            iconSelected = R.drawable.task_selected,
            iconUnSelected = R.drawable.tasks_unselected
        ),
        NavigationItem(
            route = Screen.CategoriesScreen.route,
            iconSelected = R.drawable.categories_selected,
            iconUnSelected = R.drawable.categories_unselected
        )
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(74.dp)
            .background(Theme.color.surfaceHigh),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            navigationItems.forEachIndexed { index, navigationItem ->
                NavigationIconItem(
                    navigationItem = navigationItem,
                    isSelected = selectedItem.intValue == index,
                    onClick = {
                        if (selectedItem.intValue != index) {
                            selectedItem.intValue = index
                            navController.navigate(navigationItem.route)
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun NavigationIconItem(
    navigationItem: NavigationItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(42.dp)
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = null
            )
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) Theme.color.primaryVariant else Theme.color.surfaceHigh),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(if (isSelected) navigationItem.iconSelected else navigationItem.iconUnSelected),
            tint = if (isSelected) Theme.color.primary else Theme.color.hint,
            contentDescription = "navigation icon",
            modifier = Modifier.size(24.dp)
        )
    }
}

data class NavigationItem(
    val iconSelected: Int,
    val iconUnSelected: Int,
    val route: String
)

@Preview
@Composable
fun NavBarPreview() {
    NavBar(navController = rememberNavController())
}
