package com.giraffe.tudeeapp.presentation.utils

import android.content.Context
import kotlinx.datetime.LocalDate


fun LocalDate.formatAsLocalizedDate(context: Context): String {
    val locale = context.resources.configuration.locales[0]

    val day = dayOfMonth.toString().padStart(2, '0')
    val month = monthNumber.toString().padStart(2, '0')
    val year = year.toString()

    val dateString = "$day-$month-$year"
    return if (locale.language == "ar") dateString.toArabicDigits() else dateString
}

private fun String.toArabicDigits(): String {
    val arabicDigits = mapOf(
        '0' to '٠', '1' to '١', '2' to '٢', '3' to '٣', '4' to '٤',
        '5' to '٥', '6' to '٦', '7' to '٧', '8' to '٨', '9' to '٩'
    )
    return map { arabicDigits[it] ?: it }.joinToString("")
}
