package com.example.catlogue.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val breedId: String,
    val name: String,
    val imageUrl: String?,

    val lifeSpan: String? = null,
    val origin: String? = null,
    val temperament: String? = null,
    val description: String? = null
)

