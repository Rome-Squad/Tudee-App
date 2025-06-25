package com.giraffe.tudeeapp.presentation.home.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.Slider
import com.giraffe.tudeeapp.domain.entity.task.TaskStatus
import com.giraffe.tudeeapp.presentation.home.HomeScreenState

@Composable
fun SliderStatus(state: HomeScreenState, modifier: Modifier = Modifier) {
    val slider = when {
        state.tasks.values.flatten().isEmpty() -> Slider(
            image = painterResource(R.drawable.tudee_slider_image),
            title = stringResource(R.string.nothing_on_your_list),
            subtitle = stringResource(R.string.slider_subtitle_for_empty_tasks),
            status = painterResource(R.drawable.nothing_in_list_emoji)
        )

        state.tasks[TaskStatus.IN_PROGRESS]!!.isEmpty() && state.tasks[TaskStatus.DONE]!!.isEmpty() -> Slider(
            image = painterResource(R.drawable.tudee_zero_progress),
            title = stringResource(R.string.title_zero_progress),
            subtitle = stringResource(R.string.subtitle_zero_progress),
            status = painterResource(R.drawable.zero_progress_emoji)
        )

        state.tasks[TaskStatus.DONE]!!.size == state.tasks.values.flatten().size -> Slider(
            image = painterResource(R.drawable.tudee_image_great_work),
            title = stringResource(R.string.title_great_work),
            subtitle = stringResource(R.string.subtitle_great_work),
            status = painterResource(R.drawable.great_work_emoji)
        )

        else -> Slider(
            image = painterResource(R.drawable.tudee_slider_image),
            title = stringResource(R.string.partially_completed_title),
            subtitle = stringResource(
                R.string.partially_completed_subtitle,
                state.tasks[TaskStatus.DONE]!!.size,
                state.tasks.values.flatten().size
            ),
            status = painterResource(R.drawable.stay_working_emoji),
        )
    }

    Slider(
        modifier = modifier,
        slider = slider
    )
}