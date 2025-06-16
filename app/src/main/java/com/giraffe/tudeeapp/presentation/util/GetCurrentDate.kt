package com.giraffe.tudeeapp.presentation.util

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun getTodayDate(): String {
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val day = currentDate.dayOfMonth.toString()
    val month = currentDate.month.name.lowercase()
    val year = currentDate.year
    return "$day $month $year"
}
