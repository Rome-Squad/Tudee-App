package com.giraffe.tudeeapp.presentation.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.NavBar
import com.giraffe.tudeeapp.design_system.component.TabsBar
import com.giraffe.tudeeapp.design_system.component.button_type.TudeeFabButton
import com.giraffe.tudeeapp.design_system.theme.Theme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.background(Theme.color.surface),
                title = {
                    Text("Tasks")
                }
            )
        },
        bottomBar = {
            NavBar(navController = rememberNavController())
        },
        floatingActionButton = {
            TudeeFabButton(
                isLoading = false,
                isDisable = false,
                icon = painterResource(R.drawable.add_task),
                onClick = { }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // Add the date picker here
            TabsBar()

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp),
            ) {

            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TaskScreenPreview() {
    TaskScreen()
}


