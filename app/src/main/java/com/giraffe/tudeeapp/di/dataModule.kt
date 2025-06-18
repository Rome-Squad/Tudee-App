package com.giraffe.tudeeapp.di

import androidx.core.net.toUri
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.data.database.CategoryDao
import com.giraffe.tudeeapp.data.database.TudeeDatabase
import com.giraffe.tudeeapp.data.mapper.toEntity
import com.giraffe.tudeeapp.data.util.Constants.DATABASE_NAME
import com.giraffe.tudeeapp.domain.model.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val defaultCategoryNames = listOf(
    "Education",
    "Adoration",
    "Family & friend",
    "Cooking",
    "Traveling",
    "Coding",
    "Fixing bugs",
    "Medical",
    "Shopping",
    "Agriculture",
    "Entertainment",
    "Gym",
    "Cleaning",
    "Work",
    "Event",
    "Budgeting",
    "Self-care"
)

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
    defaultCategoryNames.forEach { name ->
        val res = getCategoryIcon(name)
        val category = Category(
            name = name,
            imageUri = ("android.resource://com.giraffe.tudeeapp/$res".toUri()).toString(),
            isEditable = false,
            taskCount = 0
        ).toEntity()
        dao.createCategory(category)
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