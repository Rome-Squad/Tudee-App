package com.giraffe.tudeeapp.data.util

import com.giraffe.tudeeapp.domain.util.*
import kotlinx.coroutines.flow.Flow

suspend fun <T> safeCall(
    block: suspend () -> T
): Result<T, DomainError> {
    return try {
        Result.Success(block())
    } catch (e: Throwable) {
        Result.Error(mapExceptionToDomainError(e))
    }
}

fun <T> safeFlowCall(block: () -> Flow<T>): Result<Flow<T>, DomainError> {
    return try {
            Result.Success(block())
    } catch (e: Exception) {
        Result.Error(mapExceptionToDomainError(e))
    }
}

fun mapExceptionToDomainError(e: Throwable): DomainError {
    return when (e) {
        is NoSuchElementException -> NotFoundError()
        is IllegalArgumentException -> ValidationError()
        else -> UnknownError()
    }
}