package com.example.catlogue.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BreedDao {

    // Pega todas as raças salvas no banco
    @Query("SELECT * FROM breeds")
    suspend fun getAllBreeds(): List<BreedEntity>

    // Insere ou atualiza a lista de raças (se conflito, substitui)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBreeds(breeds: List<BreedEntity>)
}
