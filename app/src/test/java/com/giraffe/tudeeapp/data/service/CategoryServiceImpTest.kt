package com.giraffe.tudeeapp.data.service


import com.giraffe.tudeeapp.data.database.CategoryDao
import com.giraffe.tudeeapp.data.mapper.toCategory
import com.giraffe.tudeeapp.data.mapper.toEntity
import com.giraffe.tudeeapp.data.model.CategoryEntity
import com.giraffe.tudeeapp.domain.model.Category
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.giraffe.tudeeapp.domain.util.NotFoundError
import com.giraffe.tudeeapp.domain.util.Result
import com.giraffe.tudeeapp.domain.util.UnknownError
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CategoryServiceImpTest {

    private val categoryDao: CategoryDao = mockk()
    private lateinit var service: CategoriesService

    @Before
    fun setup() {
        service = CategoryServiceImp(categoryDao)
    }

    @Test
    fun `getAllCategories returns flow mapped correctly`() = runTest {
        // Given
        val entity = CategoryEntity(
            uid = 1L,
            name = "Study",
            imageUri = "content://study.png",
            isEditable = false,
            taskCount = 2
        )
        val expected = entity.toCategory()

        every { categoryDao.getAllCategories() } returns flowOf(listOf(entity))

        // When
        val result = service.getAllCategories()

        // Then
        assertTrue(result is Result.Success)
        val categories = (result as Result.Success).data.first()
        assertEquals(listOf(expected), categories)
    }

    @Test
    fun `getAllCategories returns ValidationError when DAO throws exception`() {
        every { categoryDao.getAllCategories() } throws IllegalStateException()

        val result = service.getAllCategories()

        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).error is UnknownError)
    }

    @Test
    fun `getCategoryById returns mapped Category`() = runTest {
        val id = 1L
        val entity = CategoryEntity(
            uid = id,
            name = "Work",
            imageUri = "content://image.png",
            isEditable = true,
            taskCount = 5
        )
        val expected = entity.toCategory()

        coEvery { categoryDao.getCategoryById(id) } returns entity

        val result = service.getCategoryById(id)

        assertTrue(result is Result.Success)
        assertEquals(expected, (result as Result.Success).data)
    }

    @Test
    fun `getCategoryById returns NotFoundError on NoSuchElementException`() = runTest {
        coEvery { categoryDao.getCategoryById(any()) } throws NoSuchElementException()

        val result = service.getCategoryById(99L)

        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).error is NotFoundError)
    }

    @Test
    fun `createCategory returns inserted id`() = runTest {
        val category = Category(
            id = 0L,
            name = "New",
            imageUri = "content://new.png",
            isEditable = true,
            taskCount = 0
        )

        val entity = mockk<CategoryEntity>()

        mockkStatic("com.giraffe.tudeeapp.data.mapper.CategoryMapperKt")
        every { category.toEntity() } returns entity
        coEvery { categoryDao.createCategory(entity) } returns 42L

        val result = service.createCategory(category)

        assertTrue(result is Result.Success)
        assertEquals(42L, (result as Result.Success).data)
    }

    @Test
    fun `updateCategory maps and calls DAO`() = runTest {
        val category = Category(
            id = 0L,
            name = "New",
            imageUri = "content://new.png",
            isEditable = true,
            taskCount = 0
        )

        val entity = mockk<CategoryEntity>()

        mockkStatic("com.giraffe.tudeeapp.data.mapper.CategoryMapperKt")
        every { category.toEntity() } returns entity
        coEvery { categoryDao.updateCategory(entity) } just Runs

        val result = service.updateCategory(category)

        assertTrue(result is Result.Success)
        coVerify { categoryDao.updateCategory(entity) }
    }

    @Test
    fun `deleteCategory calls DAO and returns success`() = runTest {
        val id = 77L
        coEvery { categoryDao.deleteCategory(id) } just Runs

        val result = service.deleteCategory(id)

        assertTrue(result is Result.Success)
        coVerify { categoryDao.deleteCategory(id) }
    }
}
