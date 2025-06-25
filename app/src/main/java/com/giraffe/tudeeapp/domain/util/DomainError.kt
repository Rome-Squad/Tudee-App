package com.giraffe.tudeeapp.domain.util

interface DomainError

class NotFoundError : DomainError
class ValidationError : DomainError
class UnknownError : DomainError