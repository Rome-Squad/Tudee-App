package com.giraffe.tudeeapp.presentation.home

import com.giraffe.tudeeapp.domain.util.DomainError

sealed interface HomeEvent {
    data class NavigateToTasksScreen(val tabIndex: Int): HomeEvent
    data class Error(val error: DomainError): HomeEvent
}