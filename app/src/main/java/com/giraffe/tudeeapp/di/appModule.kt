package com.giraffe.tudeeapp.di

import com.giraffe.tudeeapp.data.service.SplashServiceImpl
import com.giraffe.tudeeapp.domain.service.SplashService
import com.giraffe.tudeeapp.presentation.splash.viewmodel.SplashViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<SplashService> { SplashServiceImpl(androidApplication()) }

    viewModel { SplashViewModel(get()) }
}