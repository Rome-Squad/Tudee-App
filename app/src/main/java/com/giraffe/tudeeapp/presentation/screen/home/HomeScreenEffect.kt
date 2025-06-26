package com.giraffe.tudeeapp.presentation.screen.home

sealed interface HomeScreenEffect {
    data class NavigateToTasksScreen(val tabIndex: Int): HomeScreenEffect
    data class Error(val error: Throwable): HomeScreenEffect
}