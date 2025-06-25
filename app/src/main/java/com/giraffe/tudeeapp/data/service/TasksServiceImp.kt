package com.giraffe.tudeeapp.data.service

import TaskDao
import com.giraffe.tudeeapp.data.database.CategoryDao
import com.giraffe.tudeeapp.data.mapper.toDto
import com.giraffe.tudeeapp.data.mapper.toEntity
import com.giraffe.tudeeapp.data.mapper.toEntityList
import com.giraffe.tudeeapp.data.util.safeCall
import com.giraffe.tudeeapp.data.util.safeFlowCall
import com.giraffe.tudeeapp.domain.entity.task.Task
import com.giraffe.tudeeapp.domain.entity.task.TaskStatus
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.domain.util.DomainError
import com.giraffe.tudeeapp.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.LocalDate

class TasksServiceImp(
    private val taskDao: TaskDao,
    private val categoryDao: CategoryDao
    ) : TasksService {
    override fun getTasksByDate(date: LocalDate): Result<Flow<List<Task>>, DomainError> {
        return safeFlowCall {
            val tasksFlow = taskDao.getTasksByDate(date.toString())
            val categoriesFlow = categoryDao.getAllCategories()

            combine(tasksFlow, categoriesFlow) { tasks, categories ->
                tasks.toEntityList(categories)
            }
        }
    }

    override fun getTasksByCategory(categoryId: Long): Result<Flow<List<Task>>, DomainError> {
        return safeFlowCall {
            val tasksFlow = taskDao.getTasksByCategory(categoryId)
            val categoriesFlow = categoryDao.getAllCategories()

            combine(tasksFlow, categoriesFlow) { tasks, categories ->
                tasks.toEntityList(categories)
            }
        }
    }

    override suspend fun getTaskById(id: Long): Result<Task, DomainError> {
        return safeCall {
            val taskEntity = taskDao.getTaskById(id)
            val category = categoryDao.getCategoryById(taskEntity.categoryId)
            taskEntity.toEntity(category)
        }
    }

    override suspend fun createTask(task: Task): Result<Long, DomainError> {
        return safeCall {
            val dataTask = task.toDto()
            taskDao.createTask(dataTask)
        }
    }

    override suspend fun updateTask(task: Task): Result<Unit, DomainError> {
        return safeCall {
            val dataTask = task.toDto()
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