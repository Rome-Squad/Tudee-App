package com.giraffe.tudeeapp.di

import androidx.core.net.toUri
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import com.giraffe.tudeeapp.R
import com.giraffe.tudeeapp.data.database.CategoryDao
import com.giraffe.tudeeapp.data.database.TudeeDatabase
import com.giraffe.tudeeapp.data.mapper.toDto
import com.giraffe.tudeeapp.data.preferences.DataStorePreferences
import com.giraffe.tudeeapp.data.util.DatabaseConstants.DATABASE_NAME
import com.giraffe.tudeeapp.domain.entity.Category
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
        "Education" -> R.drawable.book_open
        "Adoration" -> R.drawable.quran
        "Family & friend" -> R.drawable.user_multiple
        "Cooking" -> R.drawable.chef
        "Traveling" -> R.drawable.airplane
        "Coding" -> R.drawable.developer
        "Fixing bugs" -> R.drawable.bug
        "Medical" -> R.drawable.hospital_location
        "Shopping" -> R.drawable.shopping_cart
        "Agriculture" -> R.drawable.plant
        "Entertainment" -> R.drawable.baseball
        "Gym" -> R.drawable.body_part_muscle
        "Cleaning" -> R.drawable.blush_brush
        "Work" -> R.drawable.briefcase
        "Event" -> R.drawable.birthday_cake
        "Budgeting" -> R.drawable.money_bag
        "Self-care" -> R.drawable.in_love
        else -> R.drawable.book_open
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
        ).toDto()
        dao.createCategory(category)
    }
}

val dataModule = module {
    single { DataStorePreferences(androidContext()) }

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