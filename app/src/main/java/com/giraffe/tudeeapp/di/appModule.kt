package com.giraffe.tudeeapp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.SavedStateHandle
import com.giraffe.tudeeapp.data.service.CategoryServiceImp
import com.giraffe.tudeeapp.data.service.TasksServiceImp
import com.giraffe.tudeeapp.data.service.TudeeAppServiceImpl
import com.giraffe.tudeeapp.data.util.tudeeDataStore
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.domain.service.TudeeAppService
import com.giraffe.tudeeapp.presentation.MainViewModel
import com.giraffe.tudeeapp.presentation.categories.CategoryViewModel
import com.giraffe.tudeeapp.presentation.home.HomeViewModel
import com.giraffe.tudeeapp.presentation.splash.SplashViewModel
import com.giraffe.tudeeapp.presentation.taskdetails.TaskDetailsViewModel
import com.giraffe.tudeeapp.presentation.taskeditor.TaskEditorViewModel
import com.giraffe.tudeeapp.presentation.tasks.TasksViewModel
import com.giraffe.tudeeapp.presentation.tasksbycategory.TasksByCategoryViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<DataStore<Preferences>> { androidContext().tudeeDataStore }
    single<TudeeAppService> { TudeeAppServiceImpl(get()) }

    single<TasksService> { TasksServiceImp(get(), get()) }
    single<CategoriesService> { CategoryServiceImp(get()) }


    viewModel { SplashViewModel(get()) }
    viewModel { (handle: SavedStateHandle) ->
        TasksViewModel(get(), handle)
    }
    viewModel { (taskId: Long) -> TaskDetailsViewModel(taskId, get()) }
    viewModel { TaskEditorViewModel(get(), get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { MainViewModel(get()) }
    viewModel { CategoryViewModel(get()) }
    viewModel { (handle: SavedStateHandle) ->
        TasksByCategoryViewModel(get(), get(), handle)
    }
}