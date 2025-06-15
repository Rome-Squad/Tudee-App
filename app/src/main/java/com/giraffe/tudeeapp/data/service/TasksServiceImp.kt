package com.giraffe.tudeeapp.data.service

import TaskDao
import com.giraffe.tudeeapp.data.mapper.toData
import com.giraffe.tudeeapp.data.mapper.toDomain
import com.giraffe.tudeeapp.data.util.safeCall
import com.giraffe.tudeeapp.domain.model.task.Task
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.domain.util.DomainError
import com.giraffe.tudeeapp.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.LocalDateTime

public class TasksServiceImp(
    private val taskDao: TaskDao,

    ) : TasksService {
    override fun getTasksByDate(date: LocalDateTime): Flow<Result<List<Task>, DomainError>> {
        return flow {
            try {
                taskDao.getTasksByDate(date)
                    .collect { entityList ->
                        val tasks = entityList.map { it.toDomain() }
                        emit(Result.Success(tasks))
                    }
            } catch (e: Throwable) {
                emit(Result.Error(error(e)))
            }
        }
    }

    override fun getTasksByCategory(categoryId: Long): Flow<Result<List<Task>, DomainError>> {
        return flow {
            try {
                taskDao.getTasksByCategory(categoryId)
                    .collect { entityList ->
                        val tasks = entityList.map { it.toDomain() }
                        emit(Result.Success(tasks))
                    }
            } catch (e: Throwable) {
                emit(Result.Error(error(e)))
            }
        }
    }

    override suspend fun getTaskById(id: Long): Result<Task, DomainError> {
        return safeCall {
            taskDao.getTaskById(id).toDomain()
        }
    }

    override suspend fun createTask(task: Task): Result<Long, DomainError> {
        return safeCall {
            val dataTask = task.toData()
            taskDao.createTask(dataTask)
        }
    }

    override suspend fun updateTask(task: Task): Result<Unit, DomainError> {
        return safeCall {
            val dataTask = task.toData()
            taskDao.updateTask(dataTask)
        }
    }

    override suspend fun deleteTask(id: Long): Result<Unit, DomainError> {
        return safeCall {
            taskDao.deleteTask(id)
        }
    }

    override suspend fun changeStatus(
        id: Long,
        newStatus: TaskStatus
    ): Result<Unit, DomainError> {
        return safeCall {
            taskDao.changeStatus(id, newStatus)
        }
    }
}
