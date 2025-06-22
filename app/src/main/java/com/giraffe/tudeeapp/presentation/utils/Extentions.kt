package com.giraffe.tudeeapp.presentation.utils

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.SnakeBarType
import com.giraffe.tudeeapp.domain.model.task.TaskPriority
import com.giraffe.tudeeapp.domain.model.task.TaskStatus

@Composable
fun TaskStatus.toStringResource() = when (this) {
    TaskStatus.TODO -> stringResource(R.string.to_do)
    TaskStatus.IN_PROGRESS -> stringResource(R.string.in_progress)
    else -> stringResource(R.string.done)
}

@Composable
fun TaskPriority.toStringResource() = when (this) {
    TaskPriority.HIGH -> stringResource(R.string.high)
    TaskPriority.MEDIUM -> stringResource(R.string.medium)
    TaskPriority.LOW -> stringResource(R.string.low)
}

suspend fun SnackbarHostState.showSuccessSnackbar(message: String) {
    showSnackbar(message, SnakeBarType.SUCCESS.name, duration = SnackbarDuration.Short)
}

suspend fun SnackbarHostState.showErrorSnackbar(message: String) {
    showSnackbar(message, SnakeBarType.ERROR.name, duration = SnackbarDuration.Short)
}