package com.giraffe.tudeeapp.presentation.utils

import android.content.Context

fun kotlinx.datetime.LocalDate.formatAsLocalizedDate(context: Context): String {
    val locale = context.resources.configuration.locales[0]

    val day = dayOfMonth.toString().padStart(2, '0')
    val monthName = when (locale.language) {
        "ar" -> arabicMonths[monthNumber - 1]
        else -> englishMonths[monthNumber - 1]
    }
    val yearStr = year.toString()

    val dateString = "$day-$monthName-$yearStr"
    return if (locale.language == "ar") dateString.toArabicDigits() else dateString
}

private val arabicMonths = listOf(
    "٠١", "٠٢", "٠٣", "٠٤", "٠٥", "٠٦",
    "٠٧", "٠٨", "٠٩", "١٠", "١١", "١٢"
)

private val englishMonths = listOf(
    "01", "02", "03", "04", "05", "06",
    "07", "08", "09", "10", "11", "12"
)


private fun String.toArabicDigits(): String {
    val arabicDigits = mapOf(
        '0' to '٠', '1' to '١', '2' to '٢', '3' to '٣', '4' to '٤',
        '5' to '٥', '6' to '٦', '7' to '٧', '8' to '٨', '9' to '٩'
    )
    return this.map { arabicDigits[it] ?: it }.joinToString("")
}
