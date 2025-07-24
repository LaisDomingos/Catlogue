package com.example.catlogue.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Embedded

@Entity(tableName = "breeds")
data class BreedEntity(
    @PrimaryKey val id: String,
    val name: String,
    val origin: String,
    val temperament: String,
    val description: String,
    val lifeSpan: String?,
    @Embedded val weight: WeightEntity,
    @Embedded val image: BreedImageEntity?
)

data class WeightEntity(
    val imperial: String,
    val metric: String
)

data class BreedImageEntity(
    val id: String?,
    val width: Int?,
    val height: Int?,
    val url: String
)
