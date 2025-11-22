package com.practice.recipesapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
data class Recipe(
    var img: String = "",
    var tittle: String = "",
    var des: String = "",
    var ing: String = "",
    var category: String = "",
    var isFavorite: Int = 0, // 0 false, 1 true
    var steps: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}
