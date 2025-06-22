package com.giraffe.tudeeapp.presentation.utils

import androidx.compose.ui.text.intl.Locale
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun getTodayDate(): String {
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val day = currentDate.dayOfMonth.toString()
    val month = getMonthName(currentDate.month.name.lowercase())
    val year = currentDate.year
    return if (isArabicLocale()) {
        "${convertToArabicNumbers(day)} $month ${convertToArabicNumbers(year.toString())}"
    } else {
        "$day $month $year"
    }
}

private fun isArabicLocale(): Boolean {
    val currentLocale = Locale.current.language
    return currentLocale == "ar"
}

private fun getMonthName(englishMonthName: String): String {
    return if (isArabicLocale()) {
        when (englishMonthName) {
            "january" -> "يناير"
            "february" -> "فبراير"
            "march" -> "مارس"
            "april" -> "أبريل"
            "may" -> "مايو"
            "june" -> "يونيو"
            "july" -> "يوليو"
            "august" -> "أغسطس"
            "september" -> "سبتمبر"
            "october" -> "أكتوبر"
            "november" -> "نوفمبر"
            "december" -> "ديسمبر"
            else -> englishMonthName
        }
    } else {
        englishMonthName
    }
}

fun convertToArabicNumbers(number: String): String {
    return if (isArabicLocale()) {
        number.map { char ->
            when (char) {
                '0' -> '٠'
                '1' -> '١'
                '2' -> '٢'
                '3' -> '٣'
                '4' -> '٤'
                '5' -> '٥'
                '6' -> '٦'
                '7' -> '٧'
                '8' -> '٨'
                '9' -> '٩'
                else -> char
            }
        }.joinToString("")
    } else {
        number
    }
}