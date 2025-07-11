package com.giraffe.tudeeapp.data.service


import com.giraffe.tudeeapp.data.database.CategoryDao
import com.giraffe.tudeeapp.data.mapper.toEntity
import com.giraffe.tudeeapp.data.mapper.toDto
import com.giraffe.tudeeapp.data.dto.CategoryDto
import com.giraffe.tudeeapp.domain.entity.Category
import com.giraffe.tudeeapp.domain.service.CategoriesService
import com.google.common.truth.Truth.assertThat
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

/*
@OptIn(ExperimentalCoroutinesApi::class)
class CategoryServiceImpTest {

    private val categoryDao: CategoryDao = mockk()
    private lateinit var service: CategoriesService

    @Before
    fun setup() {
        service = CategoryServiceImp(categoryDao)
    }

    @Test
    fun `getAllCategories should returns flow mapped correctly`() = runTest {
        // Given
        val entity = CategoryDto(
            uid = 1L,
            name = "Study",
            imageUri = "content://study.png",
            isEditable = false,
            taskCount = 2
        )
        val expected = entity.toEntity()

        every { categoryDao.getAllCategories() } returns flowOf(listOf(entity))

        // When
        val result = service.getAllCategories()

        // Then
        assertTrue(result is Result.Success)
        val categories = (result as Result.Success).data.first()
        assertEquals(listOf(expected), categories)
    }

    @Test
    fun `getAllCategories should returns ValidationError when DAO throws exception`() {
        every { categoryDao.getAllCategories() } throws IllegalStateException()

        val result = service.getAllCategories()

        assertThat(result is Result.Error).isTrue()
        assertThat((result as Result.Error).error is UnknownError).isTrue()
    }

    @Test
    fun `getCategoryById should returns mapped Category`() = runTest {
        val id = 1L
        val entity = CategoryDto(
            uid = id,
            name = "Work",
            imageUri = "content://image.png",
            isEditable = true,
            taskCount = 5
        )
        val expected = entity.toEntity()

        coEvery { categoryDao.getCategoryById(id) } returns entity

        val result = service.getCategoryById(id)

        assertThat(result is Result.Success).isTrue()
        assertThat((result as Result.Success).data).isEqualTo(expected)
    }

    @Test
    fun `getCategoryById should returns NotFoundError on NoSuchElementException`() = runTest {
        coEvery { categoryDao.getCategoryById(any()) } throws NoSuchElementException()

        val result = service.getCategoryById(99L)

        assertThat(result is Result.Error).isTrue()
        assertThat((result as Result.Error).error is NotFoundError).isTrue()
    }

    @Test
    fun `createCategory should returns inserted id`() = runTest {
        val category = Category(
            id = 0L,
            name = "New",
            imageUri = "content://new.png",
            isEditable = true,
            taskCount = 0
        )

        val entity = mockk<CategoryDto>()

        mockkStatic("com.giraffe.tudeeapp.data.mapper.CategoryMapperKt")
        every { category.toDto() } returns entity
        coEvery { categoryDao.createCategory(entity) } returns 42L

        val result = service.createCategory(category)

        assertThat(result is Result.Success).isTrue()
        assertThat((result as Result.Success).data).isEqualTo(42L)
    }

    @Test
    fun `updateCategory should map and call DAO`() = runTest {
        val category = Category(
            id = 0L,
            name = "New",
            imageUri = "content://new.png",
            isEditable = true,
            taskCount = 0
        )

        val entity = mockk<CategoryDto>()

        mockkStatic("com.giraffe.tudeeapp.data.mapper.CategoryMapperKt")
        every { category.toDto() } returns entity
        coEvery { categoryDao.updateCategory(entity) } just Runs

        val result = service.updateCategory(category)

        assertThat(result is Result.Success).isTrue()
        coVerify { categoryDao.updateCategory(entity) }
    }

    @Test
    fun `deleteCategory should call DAO and return success`() = runTest {
        val id = 77L
        coEvery { categoryDao.deleteCategory(id) } just Runs

        val result = service.deleteCategory(id)

        assertThat(result is Result.Success).isTrue()
        coVerify { categoryDao.deleteCategory(id) }
    }
}
*/
