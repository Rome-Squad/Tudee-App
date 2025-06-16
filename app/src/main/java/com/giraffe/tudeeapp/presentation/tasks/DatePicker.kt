package com.giraffe.tudeeapp.presentation.tasks

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.design_system.component.DayCard
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme
import kotlinx.datetime.LocalDateTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale


data class DayData(
    val date: LocalDate,
    val dayName: String,
    val dayNumber: Int
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    setDate: (LocalDateTime) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var isDialogVisible by remember { mutableStateOf(false) }

    val displayedDays by remember(selectedDate) {
        mutableStateOf(
            (-15..15).map {
                val date = selectedDate.plusDays(it.toLong())
                DayData(
                    date = date,
                    dayName = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
                    dayNumber = date.dayOfMonth
                )
            }
        )
    }

    Column(modifier = modifier.fillMaxWidth()) {
        MonthHeader(
            monthYearLabel = selectedDate.format(DateTimeFormatter.ofPattern("MMM, yyyy")),
            onPreviousClick = { selectedDate = selectedDate.minusMonths(1) },
            onNextClick = { selectedDate = selectedDate.plusMonths(1) },
            onMonthClick = { isDialogVisible = true }
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = displayedDays, key = { it.date }) { dayData ->
                DayCard(
                    dayNumber = dayData.dayNumber,
                    dayName = dayData.dayName,
                    isSelected = dayData.date == selectedDate,
                    onClick = { selectedDate = dayData.date }
                )
            }
        }
    }

    if (isDialogVisible) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedDate.toEpochDay() * 24 * 60 * 60 * 1000
        )
        DatePickerDialog(
            onDismissRequest = { isDialogVisible = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            selectedDate = java.time.Instant.ofEpochMilli(millis)
                                .atZone(java.time.ZoneId.systemDefault())
                                .toLocalDate()
                            setDate(convertToLocalDateTime(selectedDate))
                        }
                        isDialogVisible = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { isDialogVisible = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun convertToLocalDateTime(date: LocalDate): LocalDateTime {
    return LocalDateTime(
        year = date.year,
        monthNumber = date.monthValue,
        dayOfMonth = date.dayOfMonth,
        hour = 0,
        minute = 0,
        second = 0,
        nanosecond = 0
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DatePickerComponentPreview() {
    TudeeTheme {
        DatePicker()
    }
}