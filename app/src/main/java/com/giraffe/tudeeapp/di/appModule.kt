package com.giraffe.tudeeapp.di

import com.giraffe.tudeeapp.data.service.CategoryServiceImp
import com.giraffe.tudeeapp.data.service.SplashServiceImpl
import com.giraffe.tudeeapp.data.service.TasksServiceImp
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.service.SplashService
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.presentation.home.HomeViewModel
import com.giraffe.tudeeapp.presentation.shared.addedittask.TaskEditorViewModel
import com.giraffe.tudeeapp.presentation.shared.taskdetails.TaskDetailsViewModel
import com.giraffe.tudeeapp.presentation.splash.viewmodel.SplashViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<SplashService> { SplashServiceImpl(androidApplication()) }
    single<TasksService> { TasksServiceImp(get()) }
    single<CategoriesService> { CategoryServiceImp(get()) }


    viewModel { SplashViewModel(get()) }
    viewModel { (taskId: Long) -> TaskDetailsViewModel(taskId, get(), get()) }
    viewModel { (taskId: Long?) -> TaskEditorViewModel(taskId, get(), get()) }
    viewModel { HomeViewModel(get(), get()) }
}