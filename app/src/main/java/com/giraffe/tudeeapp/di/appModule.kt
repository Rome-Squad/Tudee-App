package com.giraffe.tudeeapp.di

import com.giraffe.tudeeapp.data.service.SplashServiceImpl
import com.giraffe.tudeeapp.domain.service.SplashService
import com.giraffe.tudeeapp.presentation.splash.viewmodel.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<SplashService> { SplashServiceImpl(get()) }

    viewModel { SplashViewModel(get()) }
}