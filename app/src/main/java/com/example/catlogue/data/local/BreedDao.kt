package com.example.catlogue.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BreedDao {

    @Query("SELECT * FROM breeds")
    fun getAllBreeds(): List<BreedEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBreeds(breeds: List<BreedEntity>)

    // Método paginado: usa LIMIT e OFFSET para pegar pedaço do banco
    @Query("SELECT * FROM breeds LIMIT :limit OFFSET :offset")
    fun getBreedsPaginated(limit: Int, offset: Int): List<BreedEntity>
}

