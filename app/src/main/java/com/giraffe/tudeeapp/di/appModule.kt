package com.giraffe.tudeeapp.di

import androidx.lifecycle.SavedStateHandle
import com.giraffe.tudeeapp.data.service.CategoryServiceImp
import com.giraffe.tudeeapp.data.service.MainServiceImpl
import com.giraffe.tudeeapp.data.service.SplashServiceImpl
import com.giraffe.tudeeapp.data.service.TasksServiceImp
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.service.MainService
import com.giraffe.tudeeapp.domain.service.SplashService
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.presentation.categories.CategoryViewModel
import com.giraffe.tudeeapp.presentation.MainViewModel
import com.giraffe.tudeeapp.presentation.home.HomeViewModel
import com.giraffe.tudeeapp.presentation.taskeditor.TaskEditorViewModel
import com.giraffe.tudeeapp.presentation.taskdetails.TaskDetailsViewModel
import com.giraffe.tudeeapp.presentation.splash.viewmodel.SplashViewModel
import com.giraffe.tudeeapp.presentation.tasks.TasksViewModel
import com.giraffe.tudeeapp.presentation.tasksbycategory.TasksByCategoryViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<SplashService> { SplashServiceImpl(androidApplication()) }
    single<MainService> { MainServiceImpl(androidApplication()) }
    single<TasksService> { TasksServiceImp(get()) }
    single<CategoriesService> { CategoryServiceImp(get()) }


    viewModel { SplashViewModel(get()) }
    viewModel { (handle: SavedStateHandle) ->
        TasksViewModel(get(), get(), handle)
    }
    viewModel { (taskId: Long) -> TaskDetailsViewModel(taskId, get(), get()) }
    viewModel { (taskId: Long?) -> TaskEditorViewModel(taskId, get(), get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { MainViewModel(get()) }
    viewModel { CategoryViewModel(get()) }
    viewModel { (handle: SavedStateHandle) ->
        TasksByCategoryViewModel(get(), get(), handle)
    }
}