package com.giraffe.tudeeapp.di

import androidx.lifecycle.SavedStateHandle
import com.giraffe.tudeeapp.presentation.app.AppViewModel
import com.giraffe.tudeeapp.presentation.screen.categories.CategoryViewModel
import com.giraffe.tudeeapp.presentation.screen.home.HomeViewModel
import com.giraffe.tudeeapp.presentation.screen.taskdetails.TaskDetailsViewModel
import com.giraffe.tudeeapp.presentation.screen.taskeditor.TaskEditorViewModel
import com.giraffe.tudeeapp.presentation.screen.tasks.TasksViewModel
import com.giraffe.tudeeapp.presentation.screen.tasksbycategory.TasksByCategoryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { (handle: SavedStateHandle) -> TasksViewModel(get(), handle) }
    viewModel { (taskId: Long) -> TaskDetailsViewModel(taskId, get()) }
    viewModel { TaskEditorViewModel(get(), get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { AppViewModel(get()) }
    viewModel { CategoryViewModel(get()) }
    viewModel { (handle: SavedStateHandle) -> TasksByCategoryViewModel(get(), get(), handle) }
}