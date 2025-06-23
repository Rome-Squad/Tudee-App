package com.giraffe.tudeeapp.data.util

import com.giraffe.tudeeapp.domain.util.DomainError
import com.giraffe.tudeeapp.domain.util.NotFoundError
import com.giraffe.tudeeapp.domain.util.Result
import com.giraffe.tudeeapp.domain.util.UnknownError
import com.giraffe.tudeeapp.domain.util.ValidationError
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