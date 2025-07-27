package com.example.catlogue.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [BreedEntity::class, FavoriteEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun breedDao(): BreedDao
    abstract fun favoriteDao(): FavoriteDao
}
