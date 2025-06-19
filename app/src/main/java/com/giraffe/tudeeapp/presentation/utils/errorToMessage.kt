package com.giraffe.tudeeapp.presentation.utils

import com.giraffe.tudeeapp.domain.util.DomainError
import com.giraffe.tudeeapp.domain.util.NotFoundError
import com.giraffe.tudeeapp.domain.util.ValidationError

fun errorToMessage(error: DomainError): String = when (error) {
    is NotFoundError -> "Task Not Found!"
    is ValidationError -> "Sorry, Something Went Wrong"
    else -> "Sorry, An Unknown Error"
}