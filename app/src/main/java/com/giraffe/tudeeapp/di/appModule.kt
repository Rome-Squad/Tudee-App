package com.giraffe.tudeeapp.di

import androidx.lifecycle.SavedStateHandle
import com.giraffe.tudeeapp.data.service.AppServiceImpl
import com.giraffe.tudeeapp.data.service.CategoryServiceImp
import com.giraffe.tudeeapp.data.service.TasksServiceImp
import com.giraffe.tudeeapp.domain.service.AppService
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.presentation.AppViewModel
import com.giraffe.tudeeapp.presentation.categories.CategoryViewModel
import com.giraffe.tudeeapp.presentation.home.HomeViewModel
import com.giraffe.tudeeapp.presentation.taskdetails.TaskDetailsViewModel
import com.giraffe.tudeeapp.presentation.taskeditor.TaskEditorViewModel
import com.giraffe.tudeeapp.presentation.tasks.TasksViewModel
import com.giraffe.tudeeapp.presentation.screen.tasksbycategory.TasksByCategoryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<AppService> { AppServiceImpl(get()) }
    single<TasksService> { TasksServiceImp(get(), get()) }
    single<CategoriesService> { CategoryServiceImp(get()) }

    viewModel { (handle: SavedStateHandle) -> TasksViewModel(get(), handle) }
    viewModel { (taskId: Long) -> TaskDetailsViewModel(taskId, get()) }
    viewModel { TaskEditorViewModel(get(), get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { AppViewModel(get()) }
    viewModel { CategoryViewModel(get()) }
    viewModel { (handle: SavedStateHandle) -> TasksByCategoryViewModel(get(), get(), handle) }
}