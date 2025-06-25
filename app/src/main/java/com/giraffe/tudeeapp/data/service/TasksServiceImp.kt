package com.giraffe.tudeeapp.data.service

import TaskDao
import com.giraffe.tudeeapp.data.database.CategoryDao
import com.giraffe.tudeeapp.data.mapper.toDto
import com.giraffe.tudeeapp.data.mapper.toEntity
import com.giraffe.tudeeapp.data.mapper.toEntityList
import com.giraffe.tudeeapp.data.util.safeCall
import com.giraffe.tudeeapp.domain.entity.task.Task
import com.giraffe.tudeeapp.domain.entity.task.TaskStatus
import com.giraffe.tudeeapp.domain.service.TasksService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.LocalDate

class TasksServiceImp(
    private val taskDao: TaskDao,
    private val categoryDao: CategoryDao
) : TasksService {
    override fun getTasksByDate(date: LocalDate): Flow<List<Task>> {
        val tasksFlow = taskDao.getTasksByDate(date.toString())
        val categoriesFlow = categoryDao.getAllCategories()

        return combine(tasksFlow, categoriesFlow) { tasks, categories ->
            tasks.toEntityList(categories)
        }
    }

    override fun getTasksByCategory(categoryId: Long): Flow<List<Task>> {
        val tasksFlow = taskDao.getTasksByCategory(categoryId)
        val categoriesFlow = categoryDao.getAllCategories()

        return combine(tasksFlow, categoriesFlow) { tasks, categories ->
            tasks.toEntityList(categories)
        }
    }

    override suspend fun getTaskById(id: Long): Task {
        return safeCall {
            val taskEntity = taskDao.getTaskById(id)
            val category = categoryDao.getCategoryById(taskEntity.categoryId)
            taskEntity.toEntity(category)
        }
    }

    override suspend fun createTask(task: Task): Long {
        return safeCall {
            val dataTask = task.toDto()
            taskDao.createTask(dataTask)
        }
    }

    override suspend fun updateTask(task: Task) {
        return safeCall {
            val dataTask = task.toDto()
            taskDao.updateTask(dataTask)
        }
    }

    override suspend fun deleteTask(id: Long) {
        return safeCall {
            taskDao.deleteTask(id)
        }
    }

    override suspend fun changeStatus(
        id: Long,
        newStatus: TaskStatus
    ) {
        return safeCall {
            taskDao.changeStatus(id, newStatus)
        }
    }
}