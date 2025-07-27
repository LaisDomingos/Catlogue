package com.example.catlogue.data.local

import androidx.room.*

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE breedId = :breedId")
    fun removeFavorite(breedId: String)

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): List<FavoriteEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE breedId = :breedId)")
    fun isFavorite(breedId: String): Boolean
}
