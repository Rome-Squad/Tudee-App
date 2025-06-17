package com.giraffe.tudeeapp.design_system.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onDateSelected: (Long?) -> Unit
) {
    if (showDialog) {
        val datePickerState = rememberDatePickerState()

        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                color = Theme.color.surface
            ) {
                Column(
                    modifier = Modifier.padding(0.dp)
                ) {
                    DatePicker(
                        state = datePickerState,
                        colors = DatePickerDefaults.colors(
                            containerColor = Theme.color.surface,
                            selectedDayContainerColor = Theme.color.primary,
                            selectedDayContentColor = Theme.color.onPrimary,
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
                    ) {

                        Box(modifier = Modifier.weight(1f)) {
                            TextButton(
                                onClick = { onDismissRequest() },
                                text = stringResource(R.string.clear_button),
                                isLoading = false,
                                isDisable = false,
                            )
                        }
                      



                    Row(
                        modifier = Modifier.weight(2f),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        TextButton(
                            Modifier.weight(1f),
                            onClick = { onDismissRequest() },
                            text = stringResource(R.string.cancel_button),
                            isLoading = false,
                            isDisable = false,
                        )

                        TextButton(
                            Modifier.weight(1f),
                            onClick = {
                                val selectedDate = datePickerState.selectedDateMillis
                                onDateSelected(selectedDate)
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