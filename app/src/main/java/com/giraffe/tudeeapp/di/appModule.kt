package com.giraffe.tudeeapp.di

import com.giraffe.tudeeapp.data.service.AppServiceImpl
import com.giraffe.tudeeapp.data.service.CategoryServiceImp
import com.giraffe.tudeeapp.data.service.TasksServiceImp
import com.giraffe.tudeeapp.domain.service.AppService
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.service.TasksService
import org.koin.dsl.module

val serviceModule = module {
    single<AppService> { AppServiceImpl(get()) }
    single<TasksService> { TasksServiceImp(get(), get()) }
    single<CategoriesService> { CategoryServiceImp(get()) }
}