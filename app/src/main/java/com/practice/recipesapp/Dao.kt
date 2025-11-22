package com.practice.recipesapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query


@Dao
interface Dao {

    @Query("SELECT * FROM recipe")
    fun getAll(): List<Recipe?>

    @Insert
    fun insert(recipe: Recipe): Long

    @Update
    fun update(recipe: Recipe)

    @Delete
    fun delete(recipe: Recipe)

    @Query("SELECT * FROM recipe WHERE isFavorite = 1")
    fun getFavorites(): List<Recipe>

    @Query("UPDATE recipe SET isFavorite = :fav WHERE uid = :id")
    fun setFavorite(id: Int, fav: Int)

    @Query("SELECT * FROM recipe WHERE tittle LIKE :q OR des LIKE :q OR ing LIKE :q")
    fun search(q: String): List<Recipe>

    @Query("SELECT * FROM recipe WHERE uid = :id LIMIT 1")
    fun getById(id: Int): Recipe?

    // history
    @Insert
    fun insertHistory(h: History): Long

    @Query("""
        SELECT r.* FROM recipe r 
        INNER JOIN history h ON r.uid = h.recipeId 
        ORDER BY h.viewedAt DESC
    """)
    fun getHistory(): List<Recipe>
}
