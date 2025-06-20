package com.giraffe.tudeeapp.domain.service

import com.giraffe.tudeeapp.domain.model.task.Task
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.util.DomainError
import com.giraffe.tudeeapp.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

interface TasksService {

    fun getTasksByDate(date: LocalDateTime): Result<Flow<List<Task>>, DomainError>

    fun getTasksByCategory(categoryId: Long): Result<Flow<List<Task>>, DomainError>

    suspend fun getTaskById(id: Long): Result<Task, DomainError>

    suspend fun createTask(task: Task): Result<Long, DomainError>

    suspend fun updateTask(task: Task): Result<Unit, DomainError>

    suspend fun deleteTask(id: Long): Result<Unit, DomainError>

    suspend fun changeStatus(id: Long, newStatus: TaskStatus): Result<Unit, DomainError>
}