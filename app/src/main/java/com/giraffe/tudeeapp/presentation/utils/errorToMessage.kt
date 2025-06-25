package com.giraffe.tudeeapp.presentation.utils

import android.content.Context
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.domain.exceptions.NotFoundError
import com.giraffe.tudeeapp.domain.exceptions.ValidationError

fun Context.errorToMessage(error: Throwable): String = when (error) {
    is NotFoundError -> getString(R.string.task_not_found)
    is ValidationError -> getString(R.string.sorry_something_went_wrong)
    else -> getString(R.string.sorry_an_unknown_error)
}