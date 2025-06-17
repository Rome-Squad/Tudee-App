package com.giraffe.tudeeapp.di

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.core.net.toUri
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.data.database.CategoryDao
import com.giraffe.tudeeapp.data.database.TudeeDatabase
import com.giraffe.tudeeapp.data.mapper.toEntity
import com.giraffe.tudeeapp.data.util.Constants.DATABASE_NAME
import com.giraffe.tudeeapp.design_system.theme.Theme
import com.giraffe.tudeeapp.domain.model.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@Composable
fun getColorForCategoryIcon(categoryName: String): Color {
    return when (categoryName) {
        "Education" -> Theme.color.purpleAccent
        "Adoration" -> Theme.color.primary
        "Family & friend" -> Theme.color.secondary
        "Cooking" -> Theme.color.pinkAccent
        "Traveling" -> Theme.color.yellowAccent
        "Coding" -> Theme.color.purpleAccent
        "Fixing bugs" -> Theme.color.pinkAccent
        "Medical" -> Theme.color.primary
        "Shopping" -> Theme.color.secondary
        "Agriculture" -> Theme.color.greenAccent
        "Entertainment" -> Theme.color.yellowAccent
        "Gym" -> Theme.color.primary
        "Cleaning" -> Theme.color.greenAccent
        "Work" -> Theme.color.secondary
        "Event" -> Theme.color.pinkAccent
        "Budgeting" -> Theme.color.purpleAccent
        "Self-care" -> Theme.color.yellowAccent
        else -> Theme.color.purpleAccent
    }
}

fun getCategoryIcon(categoryName: String): Int {
    return when (categoryName) {
        "Education" -> R.drawable.book_open_icon
        "Adoration" -> R.drawable.quran_icon
        "Family & friend" -> R.drawable.user_multiple_icon
        "Cooking" -> R.drawable.chef
        "Traveling" -> R.drawable.airplane_01
        "Coding" -> R.drawable.developer
        "Fixing bugs" -> R.drawable.bug_01
        "Medical" -> R.drawable.hospital_location
        "Shopping" -> R.drawable.shopping_cart_02
        "Agriculture" -> R.drawable.plant_02
        "Entertainment" -> R.drawable.baseball
        "Gym" -> R.drawable.body_part_muscle
        "Cleaning" -> R.drawable.blush_brush_02
        "Work" -> R.drawable.briefcase_05
        "Event" -> R.drawable.birthday_cake_icon
        "Budgeting" -> R.drawable.money_bag_01
        "Self-care" -> R.drawable.in_love
        else -> R.drawable.book_open_icon
    }
}

suspend fun addDefaultCategories(dao: CategoryDao) {
    val categories = listOf(
        Category(
            name = "coding",
            imageUri = ("android.resource://com.giraffe.tudeeapp/${R.drawable.developer}".toUri()).toString(),
            isEditable = false,
            taskCount = 0
        ),
        Category(
            name = "bugs",
            imageUri = ("android.resource://com.giraffe.tudeeapp/${R.drawable.bug_01}".toUri()).toString(),
            isEditable = false,
            taskCount = 0
        ),
        Category(
            name = "gym",
            imageUri = ("android.resource://com.giraffe.tudeeapp/${R.drawable.body_part_muscle}".toUri()).toString(),
            isEditable = false,
            taskCount = 0
        ),
        Category(
            name = "travel",
            imageUri = ("android.resource://com.giraffe.tudeeapp/${R.drawable.airplane_01}".toUri()).toString(),
            isEditable = false,
            taskCount = 0
        ),
    )
    categories.forEach {
        dao.createCategory(it.toEntity())
    }

}


val dataModule = module {
    single {
        val database = get<TudeeDatabase>()
        database.taskDao()
    }

    single {
        val database = get<TudeeDatabase>()
        database.categoryDao()
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            TudeeDatabase::class.java,
            DATABASE_NAME,
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(connection: SQLiteConnection) {
                super.onCreate(connection)
                CoroutineScope(Dispatchers.IO).launch {
                    addDefaultCategories(get())
                }
            }
        }).build()
    }
}