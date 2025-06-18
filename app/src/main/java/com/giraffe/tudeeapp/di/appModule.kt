package com.giraffe.tudeeapp.di

import androidx.lifecycle.SavedStateHandle
import com.giraffe.tudeeapp.data.service.CategoryServiceImp
import com.giraffe.tudeeapp.data.service.SplashServiceImpl
import com.giraffe.tudeeapp.data.service.TasksServiceImp
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.service.SplashService
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.presentation.categories.viewmodel.CategoryViewModel
import com.giraffe.tudeeapp.presentation.categories.tasks_by_category.TasksByCategoryViewModel
import com.giraffe.tudeeapp.presentation.splash.viewmodel.SplashViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<SplashService> { SplashServiceImpl(androidApplication()) }
    single<TasksService> { TasksServiceImp(get()) }
    single<CategoriesService> { CategoryServiceImp(get()) }


    viewModel { SplashViewModel(get()) }
    viewModel { CategoryViewModel(get()) }
    viewModel { (handle: SavedStateHandle) ->
        TasksByCategoryViewModel(get(), get(), handle)
    }
}