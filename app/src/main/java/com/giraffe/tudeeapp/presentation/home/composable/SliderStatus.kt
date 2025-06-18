package com.giraffe.tudeeapp.presentation.home.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.Slider
import com.giraffe.tudeeapp.presentation.home.HomeContent
import com.giraffe.tudeeapp.presentation.home.HomeUiState

@Composable
fun SliderStatus(state: HomeUiState, modifier: Modifier = Modifier) {
    val slider = when {
        state.allTasksCount == 0 -> Slider(
            image = painterResource(R.drawable.tudee_slider_image),
            title = stringResource(R.string.nothing_on_your_list),
            subtitle = stringResource(R.string.slider_subtitle_for_empty_tasks),
            status = painterResource(R.drawable.nothing_in_list_emoji)
        )

        state.inProgressTasksCount == 0 -> Slider(
            image = painterResource(R.drawable.tudee_zero_progress),
            title = stringResource(R.string.nothing_on_your_list),
            subtitle = stringResource(R.string.slider_subtitle_for_empty_tasks),
            status = painterResource(R.drawable.nothing_in_list_emoji)
        )

        state.doneTasksCount == state.allTasksCount -> Slider(
            image = painterResource(R.drawable.tudee_image_great_work),
            title = stringResource(R.string.title_great_work),
            subtitle = stringResource(R.string.subtitle_great_work),
            status = painterResource(R.drawable.great_work_emoji)
        )

        (state.doneTasksCount > 0) && (state.toDoTasksCount > state.doneTasksCount) -> Slider(
            image = painterResource(R.drawable.tudee_slider_image),
            title = stringResource(R.string.partially_completed_title),
            subtitle = stringResource(
                R.string.partially_completed_subtitle,
                state.doneTasksCount,
                state.toDoTasksCount
            ),
            status = painterResource(R.drawable.stay_working_emoji),
        )

        else -> Slider(
            image = painterResource(R.drawable.tudee_slider_image),
            title = stringResource(R.string.partially_completed_title),
            subtitle = stringResource(
                R.string.partially_completed_subtitle,
                state.doneTasksCount,
                state.toDoTasksCount
            ),
            status = painterResource(R.drawable.stay_working_emoji),
        )
    }

    Slider(
        modifier = modifier,
        slider = slider
    )
}

@Preview(widthDp = 360)
@Composable
fun Preview() {
    HomeContent(state = HomeUiState())
}