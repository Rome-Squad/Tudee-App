package com.giraffe.tudeeapp.domain.service

interface MainService {
    suspend fun setCurrentTheme(isDark: Boolean)
    suspend fun getCurrentTheme(): Boolean
}