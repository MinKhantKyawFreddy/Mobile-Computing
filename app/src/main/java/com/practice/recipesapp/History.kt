package com.practice.recipesapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class History(
    val recipeId: Int,
    val viewedAt: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
