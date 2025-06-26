package com.giraffe.tudeeapp.presentation.screen.tasks.composable

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.tudeeapp.design_system.component.DatePickerDialog
import com.giraffe.tudeeapp.design_system.component.DayCard
import com.giraffe.tudeeapp.design_system.theme.TudeeTheme
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import java.text.NumberFormat
import java.time.format.TextStyle
import java.util.Locale

data class DayData(
    val date: LocalDate,
    val dayName: String,
    val dayNumber: String
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    modifier: Modifier = Modifier,
    onDateSelected: (LocalDate) -> Unit = {}
) {
    var selectedDate by remember {
        mutableStateOf(
            Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        )
    }
    val currentMonth = selectedDate.monthNumber
    val currentYear = selectedDate.year
    var isDialogVisible by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val currentLocale = LocalConfiguration.current.locales[0]

    val daysInMonth = remember(currentYear, currentMonth) {
        Month(currentMonth).length(isLeapYear(currentYear))
    }

    val displayedDays by remember(currentYear, currentMonth) {
        mutableStateOf(
            (1..daysInMonth).map { day ->
                val date = LocalDate(currentYear, currentMonth, day)
                val dayOfWeek = date.dayOfWeek
                DayData(
                    date = date,
                    dayName = dayOfWeek.getDisplayName(TextStyle.SHORT, currentLocale),
                    dayNumber = day.toString().toInt().toLocaleNumbers(currentLocale)
                )
            }
        )
    }

    Column(modifier = modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)) {
        val monthName = Month(currentMonth).getDisplayName(TextStyle.FULL, currentLocale)
        val yearInLocal = currentYear.toLocaleNumbers(currentLocale)
        val formattedDate = "$monthName, $yearInLocal"

        MonthHeader(
            monthYearLabel = formattedDate,
            onPreviousClick = {
                selectedDate = selectedDate.minus(DatePeriod(months = 1))
                onDateSelected(selectedDate)
            },
            onNextClick = {
                selectedDate = selectedDate.plus(DatePeriod(months = 1))
                onDateSelected(selectedDate)
            },
            onMonthClick = { isDialogVisible = true }
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            state = listState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            reverseLayout = currentLocale.language == "ar"
        ) {
            items(items = displayedDays, key = { it.date.toString() }) { dayData ->
                DayCard(
                    dayNumber = dayData.dayNumber,
                    dayName = dayData.dayName,
                    isSelected = dayData.date == selectedDate,
                    onClick = {
                        selectedDate = dayData.date
                        onDateSelected(selectedDate)
                    }
                )
            }
        }
    }

    LaunchedEffect(selectedDate) {
        val selectedIndex = displayedDays.indexOfFirst { it.date == selectedDate }
        if (selectedIndex != -1) {
            coroutineScope.launch {
                listState.animateScrollToItem(index = (selectedIndex - 2).coerceAtLeast(0))
            }
        }
    }

    DatePickerDialog(
        showDialog = isDialogVisible,
        onDismissRequest = { isDialogVisible = false },
        selectedDate = selectedDate,
        onDateSelected = {
            selectedDate = LocalDate(it.year, it.monthNumber, it.dayOfMonth)
            onDateSelected(it)
        }
    )
}

private fun Int.toLocaleNumbers(locale: Locale): String {
    val formatter = NumberFormat.getInstance(locale)
    formatter.isGroupingUsed = false
    return formatter.format(this)
}

private fun isLeapYear(year: Int): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DatePickerComponentPreview() {
    TudeeTheme {
        DatePicker()
    }
}
