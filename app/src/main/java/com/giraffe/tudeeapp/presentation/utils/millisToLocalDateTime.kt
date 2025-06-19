package com.giraffe.tudeeapp.presentation.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun millisToLocalDateTime(millis: Long?): LocalDateTime {
    return millis?.let {
        Instant.fromEpochMilliseconds(it).toLocalDateTime(TimeZone.currentSystemDefault())
    } ?: Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
}