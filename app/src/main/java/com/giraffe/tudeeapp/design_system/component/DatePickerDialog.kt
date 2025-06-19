package com.giraffe.tudeeapp.design_system.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.design_system.component.button_type.TextButton
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme
import com.giraffe.tudeeapp.presentation.tasks.convertToLocalDateTime
import kotlinx.datetime.LocalDateTime
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    modifier: Modifier = Modifier,
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDateTime) -> Unit
) {
    if (showDialog) {
        var selectedDate by remember { mutableStateOf(LocalDate.now()) }
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedDate.atStartOfDay(ZoneId.of("UTC")).toInstant()
                .toEpochMilli()
        )

        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                color = Theme.color.surface
            ) {
                Column(
                ) {
                    DatePicker(
                        state = datePickerState,
                        colors = DatePickerDefaults.colors(
                            containerColor = Theme.color.surface,
                            selectedDayContainerColor = Theme.color.primary,
                            selectedDayContentColor = Theme.color.onPrimary,
                            selectedYearContentColor = Theme.color.onPrimary,
                            selectedYearContainerColor = Theme.color.primary,
                            todayContentColor = Theme.color.primary,
                            todayDateBorderColor = Theme.color.primary,
                            dayContentColor = Theme.color.title,
                            weekdayContentColor = Theme.color.title,
                            titleContentColor = Theme.color.title,
                            headlineContentColor = Theme.color.title,
                        )
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {


                        TextButton(
                            modifier = Modifier,
                            onClick = { datePickerState.selectedDateMillis = null },
                            text = stringResource(R.string.clear_button),
                            isLoading = false,
                            isDisable = false,
                        )



                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            TextButton(
                                modifier = Modifier,
                                onClick = { onDismissRequest() },
                                text = stringResource(R.string.cancel_button),
                                isLoading = false,
                                isDisable = false,
                            )

                            TextButton(
                                modifier = Modifier,
                                onClick = {
                                    datePickerState.selectedDateMillis?.let { millis ->
                                        selectedDate = Instant.ofEpochMilli(millis)
                                            .atZone(ZoneId.of("UTC"))
                                            .toLocalDate()
                                        onDateSelected(convertToLocalDateTime(selectedDate))
                                    }
                                    onDismissRequest()
                                },
                                text = stringResource(R.string.ok_button),
                                isLoading = false,
                                isDisable = false,
                            )
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun TudeeDatePickerDialogPreview() {
    TudeeTheme(false) {
        var showDialog by remember { mutableStateOf(true) }

        DatePickerDialog(
            showDialog = showDialog,
            onDismissRequest = { showDialog = false },
            onDateSelected = {
            }
        )
    }
}