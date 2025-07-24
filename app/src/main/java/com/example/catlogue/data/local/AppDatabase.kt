package com.example.catlogue.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

// Define o banco de dados com as entidades e versão
@Database(entities = [BreedEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun breedDao(): BreedDao
}
