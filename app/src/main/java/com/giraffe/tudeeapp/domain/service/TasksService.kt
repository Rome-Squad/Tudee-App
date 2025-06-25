package com.giraffe.tudeeapp.domain.service

import com.giraffe.tudeeapp.domain.entity.task.Task
import com.giraffe.tudeeapp.domain.entity.task.TaskStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface TasksService {

    fun getTasksByDate(date: LocalDate): Flow<List<Task>>
    fun getTasksByCategory(categoryId: Long): Flow<List<Task>>

    suspend fun createTask(task: Task): Long
    suspend fun getTaskById(id: Long): Task
    suspend fun updateTask(task: Task): Unit
    suspend fun deleteTask(id: Long): Unit

    suspend fun changeStatus(id: Long, newStatus: TaskStatus): Unit
}