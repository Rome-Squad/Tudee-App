package com.giraffe.tudeeapp.domain.exceptions

open class TudeeException: Exception()

class NotFoundError: TudeeException()
class ValidationError: TudeeException()
class UnknownError: TudeeException()