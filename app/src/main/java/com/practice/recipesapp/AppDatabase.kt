package com.practice.recipesapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Recipe::class, History::class], exportSchema = false, version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getDao(): Dao
}
