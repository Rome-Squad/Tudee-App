package com.giraffe.tudeeapp.data.service

import TaskDao
import com.giraffe.tudeeapp.data.mapper.toEntity
import com.giraffe.tudeeapp.data.mapper.toTask
import com.giraffe.tudeeapp.data.util.safeCall
import com.giraffe.tudeeapp.data.util.safeFlowCall
import com.giraffe.tudeeapp.domain.model.task.Task
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.domain.util.DomainError
import com.giraffe.tudeeapp.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime

class TasksServiceImp(
    private val taskDao: TaskDao,

    ) : TasksService {
    override fun getTasksByDate(date: LocalDateTime): Flow<Result<List<Task>, DomainError>> {
        return safeFlowCall {
            taskDao.getTasksByDate(date.toString())
                .map { list -> list.map { it.toTask() } }
        }
    }

    override fun getTasksByCategory(categoryId: Long): Flow<Result<List<Task>, DomainError>> {
        return safeFlowCall {
            taskDao.getTasksByCategory(categoryId)
                .map { list -> list.map { it.toTask() } }
        }
    }

    override suspend fun getTaskById(id: Long): Result<Task, DomainError> {
        return safeCall {
            taskDao.getTaskById(id).toTask()
        }
    }

    override suspend fun createTask(task: Task): Result<Long, DomainError> {
        return safeCall {
            val dataTask = task.toEntity()
            taskDao.createTask(dataTask)
        }
    }

    override suspend fun updateTask(task: Task): Result<Unit, DomainError> {
        return safeCall {
            val dataTask = task.toEntity()
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