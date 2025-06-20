package com.giraffe.tudeeapp.presentation.utils

import android.content.Context
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.domain.util.DomainError
import com.giraffe.tudeeapp.domain.util.NotFoundError
import com.giraffe.tudeeapp.domain.util.ValidationError

fun Context.errorToMessage(error: DomainError): String = when (error) {
    is NotFoundError -> getString(R.string.task_not_found)
    is ValidationError -> getString(R.string.sorry_something_went_wrong)
    else -> getString(R.string.sorry_an_unknown_error)
}