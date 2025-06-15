package com.giraffe.tudeeapp.data.util

import com.giraffe.tudeeapp.domain.util.*

suspend fun <T> safeCall(
    block: suspend () -> T
): Result<T, DomainError> {
    return try {
        Result.Success(block())
    } catch (e: Throwable) {
        val error = when (e) {
            is NoSuchElementException -> NotFoundError()
            is IllegalArgumentException -> ValidationError()
            else -> UnknownError()
        }
        Result.Error(error)
    }
}