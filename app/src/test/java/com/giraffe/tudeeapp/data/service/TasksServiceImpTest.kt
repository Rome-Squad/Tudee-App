package com.giraffe.tudeeapp.data.service
/*

import TaskDao
import com.giraffe.tudeeapp.data.database.CategoryDao
import com.giraffe.tudeeapp.data.mapper.toEntity
import com.giraffe.tudeeapp.data.mapper.toDto
import com.giraffe.tudeeapp.data.dto.CategoryDto
import com.giraffe.tudeeapp.data.dto.TaskDto
import com.giraffe.tudeeapp.domain.entity.task.Task
import com.giraffe.tudeeapp.domain.entity.task.TaskPriority
import com.giraffe.tudeeapp.domain.entity.task.TaskStatus
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
import kotlinx.datetime.LocalDate
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TasksServiceImpTest {

    private val taskDao: TaskDao = mockk()
    private val categoryDao: CategoryDao = mockk()
    private lateinit var service: TasksService

    @Before
    fun setup() {
        service = TasksServiceImp(taskDao, categoryDao)
    }

    @Test
    fun `getTaskById should return Task`() = runTest {
        // Given
        val id = 1L
        val entity = TaskDto(
            uid = id,
            title = "Test",
            description = "Description",
            dueDate = "2025-06-20",
            status = TaskStatus.TODO,
            categoryId = 5L,
            taskPriority = TaskPriority.HIGH,
            createdAt = "2025-06-20",
            updatedAt = "2025-06-20"
        )
        val categoryDto = CategoryDto(
            uid = 5L,
            name = "Work",
            imageUri = "content://sample",
            isEditable = true,
            taskCount = 10
        )
        val expectedTask = entity.toEntity(categoryDto)

        coEvery { taskDao.getTaskById(id) } returns entity
        coEvery { categoryDao.getCategoryById(5L) } returns categoryDto

        // When
        val result = service.getTaskById(id)

        // Then
        assertThat(result is Result.Success).isTrue()
        assertThat((result as Result.Success).data).isEqualTo(expectedTask)
    }

    @Test
    fun `getTaskById should return NotFoundError when DAO throws NoSuchElementException`() = runTest {
        coEvery { taskDao.getTaskById(1L) } throws NoSuchElementException()

        val result = service.getTaskById(1L)

        assertThat(result is Result.Error).isTrue()
        assertThat((result as Result.Error).error is NotFoundError).isTrue()
    }

    @Test
    fun `createTask should call DAO and return Result_Success with ID`() = runTest {
        val task = Task(
            id = 0L,
            title = "Test Task",
            description = "Test description",
            dueDate = LocalDate.parse("2025-06-20"),
            status = TaskStatus.TODO,
            category = CategoryDto(
                uid = 5L,
                name = "Work",
                imageUri = "content://sample",
                isEditable = true,
                taskCount = 10
            ).toEntity(),
            taskPriority = TaskPriority.HIGH,
            createdAt = LocalDate.parse("2025-06-20"),
            updatedAt = LocalDate.parse("2025-06-20")
        )

        val entity = mockk<TaskDto>()
        mockkStatic("com.giraffe.tudeeapp.data.mapper.TaskMapperKt")
        every { task.toDto() } returns entity
        coEvery { taskDao.createTask(entity) } returns 101L

        val result = service.createTask(task)

        assertThat(result is Result.Success).isTrue()
        assertThat((result as Result.Success).data).isEqualTo(101L)
    }

    @Test
    fun `getTasksByCategory should return mapped flow of tasks`() = runTest {
        val categoryId = 5L
        val taskDto = TaskDto(
            uid = 1L,
            title = "Task",
            description = "Description",
            dueDate = "2025-06-20",
            status = TaskStatus.TODO,
            categoryId = categoryId,
            taskPriority = TaskPriority.HIGH,
            createdAt = "2025-06-20",
            updatedAt = "2025-06-20"
        )

        val categoryDto = CategoryDto(
            uid = 5L,
            name = "Work",
            imageUri = "content://sample",
            isEditable = true,
            taskCount = 10
        )
        val expectedTask = taskDto.toEntity(categoryDto)

        val tasksFlow = flowOf(listOf(taskDto))
        val categoriesFlow = flowOf(listOf(categoryDto))

        every { taskDao.getTasksByCategory(categoryId) } returns tasksFlow
        every { categoryDao.getAllCategories() } returns categoriesFlow

        val result = service.getTasksByCategory(categoryId)

        assertThat(result is Result.Success).isTrue()
        val collected = (result as Result.Success).data.first()
        assertThat(collected).isEqualTo(listOf(expectedTask))
    }

    @Test
    fun `getTasksByDate should return mapped flow of tasks`() = runTest {
        val date = LocalDate.parse("2025-06-20")
        val taskDto = TaskDto(
            uid = 1L,
            title = "Task",
            description = "Description",
            dueDate = "2025-06-20",
            status = TaskStatus.TODO,
            categoryId = 5L,
            taskPriority = TaskPriority.HIGH,
            createdAt = "2025-06-20",
            updatedAt = "2025-06-20"
        )
        val categoryDto = CategoryDto(
            uid = 5L,
            name = "Work",
            imageUri = "content://sample",
            isEditable = true,
            taskCount = 10
        )
        val expectedTask = taskDto.toEntity(categoryDto)

        val tasksFlow = flowOf(listOf(taskDto))
        val categoriesFlow = flowOf(listOf(categoryDto))

        every { taskDao.getTasksByDate(date.toString()) } returns tasksFlow
        every { categoryDao.getAllCategories() } returns categoriesFlow

        val result = service.getTasksByDate(date)

        assertThat(result is Result.Success).isTrue()
        val collected = (result as Result.Success).data.first()
        assertThat(collected).isEqualTo(listOf(expectedTask))
    }

    @Test
    fun `deleteTask should call DAO and return Result Success`() = runTest {
        coEvery { taskDao.deleteTask(99L) } just Runs

        val result = service.deleteTask(99L)

        assertThat(result is Result.Success).isTrue()
        coVerify { taskDao.deleteTask(99L) }
    }

    @Test
    fun `changeStatus should call DAO with correct values`() = runTest {
        coEvery { taskDao.changeStatus(1L, TaskStatus.DONE) } just Runs

        val result = service.changeStatus(1L, TaskStatus.DONE)

        assertThat(result is Result.Success).isTrue()
        coVerify { taskDao.changeStatus(1L, TaskStatus.DONE) }
    }

    @Test
    fun `getTasksByCategory should return ValidationError on exception`() {
        every { taskDao.getTasksByCategory(any()) } throws IllegalArgumentException()

        val result = service.getTasksByCategory(5L)

        assertThat(result is Result.Error).isTrue()
        assertThat((result as Result.Error).error is ValidationError).isTrue()
    }
}
*/
