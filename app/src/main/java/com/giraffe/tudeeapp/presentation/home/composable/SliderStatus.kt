package com.giraffe.tudeeapp.presentation.home.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.Slider
import com.giraffe.tudeeapp.presentation.home.uistate.TasksUiState

@Composable
fun SliderStatus(state: TasksUiState) {
    when {
        state.allTasksCount == 0 -> {
            Slider(
                image = painterResource(R.drawable.tudee_slider_image),
                title = stringResource(R.string.nothing_on_your_list),
                subtitle = stringResource(R.string.slider_subtitle_for_empty_tasks),
                status = painterResource(R.drawable.tudde_emoji_empty_tasks),
            )
        }

        state.inProgressTasksCount == 0 -> {
            Slider(
                image = painterResource(R.drawable.tudee_zero_progress),
                title = stringResource(R.string.title_zero_progress),
                subtitle = stringResource(R.string.subtitle_zero_progress),
                status = painterResource(R.drawable.tudee_emoji_zero_progress),
            )
        }

        state.doneTasksCount == state.allTasksCount -> {
            Slider(
                image = painterResource(R.drawable.tudee_image_great_work),
                title = stringResource(R.string.title_great_work),
                subtitle = stringResource(R.string.subtitle_great_work),
                status = painterResource(R.drawable.tudee_emoji_great_work),
            )
        }

        (state.doneTasksCount > 0) && (state.toDoTasksCount > state.doneTasksCount) -> {
            Slider(
                image = painterResource(R.drawable.tudee_slider_image),
                title = stringResource(R.string.partially_completed_title),
                subtitle = stringResource(
                    R.string.partially_completed_subtitle,
                    state.doneTasksCount,
                    state.toDoTasksCount
                ),
                status = painterResource(R.drawable.tudee_status_partially_completed),
            )
        }
    }
}