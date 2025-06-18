package com.giraffe.tudeeapp.presentation.home.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.Slider
import com.giraffe.tudeeapp.presentation.home.HomeContent
import com.giraffe.tudeeapp.presentation.home.uistate.TasksUiState

@Composable
fun SliderStatus(state: TasksUiState,modifier: Modifier = Modifier) {
    when {

        state.allTasksCount == 0 -> {
            Slider(
                modifier = modifier,
                image = painterResource(R.drawable.tudee_slider_image),
                title = stringResource(R.string.nothing_on_your_list),
                subtitle = stringResource(R.string.slider_subtitle_for_empty_tasks),
                status = painterResource(R.drawable.nothing_in_list_emoji),
            )
            return
        }

        state.inProgressTasksCount == 0 -> {
            Slider(
                modifier = modifier,
                image = painterResource(R.drawable.tudee_zero_progress),
                title = stringResource(R.string.title_zero_progress),
                subtitle = stringResource(R.string.subtitle_zero_progress),
                status = painterResource(R.drawable.zero_progress_emoji),
            )
            return
        }

        state.doneTasksCount == state.allTasksCount -> {
            Slider(
                modifier = modifier,
                image = painterResource(R.drawable.tudee_image_great_work),
                title = stringResource(R.string.title_great_work),
                subtitle = stringResource(R.string.subtitle_great_work),
                status = painterResource(R.drawable.great_work_emoji),
            )
            return
        }

        (state.doneTasksCount > 0) && (state.toDoTasksCount > state.doneTasksCount) -> {
            Slider(
                modifier = modifier,
                image = painterResource(R.drawable.tudee_slider_image),
                title = stringResource(R.string.partially_completed_title),
                subtitle = stringResource(
                    R.string.partially_completed_subtitle,
                    state.doneTasksCount,
                    state.toDoTasksCount
                ),
                status = painterResource(R.drawable.stay_working_emoji),
            )
            return
        }
    }
}

@Preview(widthDp = 360)
@Composable
fun Preview() {
    HomeContent(state = TasksUiState())
}