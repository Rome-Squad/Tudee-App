package com.giraffe.tudeeapp.data.util

import com.giraffe.tudeeapp.domain.exceptions.*

suspend fun <T> safeCall(
    block: suspend () -> T
): T {
    return try {
        block()
    } catch (e: Throwable) {
        throw mapExceptionToTudeeError(e)
    }
}

fun mapExceptionToTudeeError(e: Throwable): Throwable {
    return when (e) {
        is NoSuchElementException -> NotFoundError()
        is IllegalArgumentException -> ValidationError()
        else -> UnknownError()
    }
}