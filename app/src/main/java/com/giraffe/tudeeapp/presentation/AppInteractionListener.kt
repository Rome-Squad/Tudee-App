package com.giraffe.tudeeapp.presentation

interface AppInteractionListener {
    fun isDarkTheme()
    fun onToggleTheme()
    fun isFirsTime()
    fun setFirsTimeStatus()
}