package com.practice.recipesapp

import android.content.Context
import androidx.room.Room

object DatabaseRepository {
    private var db: AppDatabase? = null

    fun init(context: Context) {
        if (db == null) {
            db = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java,
                "db_name")
                .allowMainThreadQueries() // okay short-term; move to background for production
                .fallbackToDestructiveMigration()
                .createFromAsset("recipe.db")
                .build()
        }
    }

    fun dao(): Dao = db!!.getDao()
}
