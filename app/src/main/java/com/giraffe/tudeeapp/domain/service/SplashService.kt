package com.giraffe.tudeeapp.domain.service

interface SplashService {
    suspend fun setOnboardingShown(shown: Boolean)
    suspend fun isOnboardingShown(): Boolean
}