package com.giraffe.tudeeapp.data.service

import TaskDao
import com.giraffe.tudeeapp.data.mapper.toEntity
import com.giraffe.tudeeapp.data.mapper.toTask
import com.giraffe.tudeeapp.data.model.TaskEntity
import com.giraffe.tudeeapp.domain.model.task.Task
import com.giraffe.tudeeapp.domain.model.task.TaskPriority
import com.giraffe.tudeeapp.domain.model.task.TaskStatus
import com.giraffe.tudeeapp.domain.service.TasksService
import com.giraffe.tudeeapp.domain.util.NotFoundError
import com.giraffe.tudeeapp.domain.util.Result
import com.giraffe.tudeeapp.domain.util.ValidationError
import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.atTime
import kotlinx.datetime.toLocalDateTime
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TasksServiceImpTest {

    private val taskDao: TaskDao = mockk()
    private lateinit var service: TasksService

    @Before
    fun setup() {
        service = TasksServiceImp(taskDao)
    }

    @Test
    fun `returns Task when getTaskById is called`() = runTest {
        val id = 1L

        val entity = TaskEntity(
            uid = 1L,
            title = "Test",
            description = "Description",
            dueDate = "2025-06-20T12:00:00",
            status = TaskStatus.TODO,
            categoryId = 1L,
            taskPriority = TaskPriority.HIGH,
            createdAt = "2025-06-20T12:00:00",
            updatedAt = "2025-06-20T12:00:00"
        )

        val expectedTask = entity.toTask()

        coEvery { taskDao.getTaskById(id) } returns entity

        val result = service.getTaskById(id)

        assertThat(result is Result.Success).isTrue()
        assertThat((result as Result.Success).data).isEqualTo(expectedTask)
    }

    @Test
    fun `getTaskById returns NotFoundError when DAO throws NoSuchElementException`() = runTest {
        coEvery { taskDao.getTaskById(1L) } throws NoSuchElementException()

        val result = service.getTaskById(1L)

        assertThat(result is Result.Error).isTrue()
        assertThat((result as Result.Error).error is NotFoundError).isTrue()
    }

    @Test
    fun `createTask calls DAO and returns Result_Success with ID when create Task`() = runTest {
        // Given
        val task = Task(
            id = 0L,
            title = "Test Task",
            description = "Test description",
            dueDate = "2025-06-20T12:00:00".toLocalDateTime(),
            status = TaskStatus.TODO,
            categoryId = 1L,
            taskPriority = TaskPriority.HIGH,
            createdAt = "2025-06-20T12:00:00".toLocalDateTime(),
            updatedAt = "2025-06-20T12:00:00".toLocalDateTime()
        )
        val entity = mockk<TaskEntity>()

        mockkStatic("com.giraffe.tudeeapp.data.mapper.TaskMapperKt")
        every { task.toEntity() } returns entity
        coEvery { taskDao.createTask(entity) } returns 101L

        // When
        val result = service.createTask(task)

        // Then
        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat((result as Result.Success).data).isEqualTo(101L)
        coVerify { taskDao.createTask(entity) }
    }

    @Test
    fun `getTasksByCategory maps flow correctly`() = runTest {
        // Given
        val categoryId = 3L
        val entity = TaskEntity(
            uid = 1L,
            title = "Test",
            description = "Description",
            dueDate = "2025-06-20T12:00:00",
            status = TaskStatus.TODO,
            categoryId = categoryId,
            taskPriority = TaskPriority.HIGH,
            createdAt = "2025-06-20T12:00:00",
            updatedAt = "2025-06-20T12:00:00"
        )
        val expectedTask = entity.toTask()

        val flow = flowOf(listOf(entity))
        every { taskDao.getTasksByCategory(categoryId) } returns flow

        // When
        val result = service.getTasksByCategory(categoryId)

        // Then
        assertThat(result is Result.Success).isTrue()
        val collected = (result as Result.Success).data.first()
        assertThat(collected).isEqualTo(listOf(expectedTask))
    }

    @Test
    fun `getTasksByCategory returns ValidationError when DAO throws exception`() {
        every { taskDao.getTasksByCategory(any()) } throws IllegalArgumentException()

        val result = service.getTasksByCategory(99L)

        assertThat(result is Result.Error).isTrue()
        assertThat((result as Result.Error).error is ValidationError).isTrue()
    }

    @Test
    fun `deleteTask calls DAO and returns Result Success`() = runTest {
        coEvery { taskDao.deleteTask(55L) } just Runs

        val result = service.deleteTask(55L)

        assertThat(result is Result.Success).isTrue()
        coVerify { taskDao.deleteTask(55L) }
    }

    @Test
    fun `changeStatus calls DAO with correct parameters`() = runTest {
        val id = 1L
        val newStatus = TaskStatus.DONE
        coEvery { taskDao.changeStatus(id, newStatus) } just Runs

        val result = service.changeStatus(id, newStatus)

        assertThat(result is Result.Success).isTrue()
        coVerify { taskDao.changeStatus(id, newStatus) }
    }

    @Test
    fun `getTasksByDate returns Result Success with mapped list`() = runTest {
        // Given
        val dateTime = LocalDateTime(2025, 6, 20, 12, 0)
        val daoDate = dateTime.date.atTime(0, 0).toString()

        val entity = TaskEntity(
            uid = 1L,
            title = "Test",
            description = "Description",
            dueDate = "2025-06-20T12:00:00",
            status = TaskStatus.TODO,
            categoryId = 1L,
            taskPriority = TaskPriority.HIGH,
            createdAt = "2025-06-20T12:00:00",
            updatedAt = "2025-06-20T12:00:00"
        )

        val expectedTask = entity.toTask()
        every { taskDao.getTasksByDate(daoDate) } returns flowOf(listOf(entity))

        // When
        val result = service.getTasksByDate(dateTime)

        // Then
        assertThat(result is Result.Success).isTrue()
        val tasks = (result as Result.Success).data.first()
        assertThat(tasks).isEqualTo(listOf(expectedTask))
    }

}
