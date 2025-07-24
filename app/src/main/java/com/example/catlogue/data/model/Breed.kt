package com.example.catlogue.data.model
import com.google.gson.annotations.SerializedName

data class Breed(
    val id: String,
    val name: String,
    val origin: String,
    val temperament: String,
    val description: String,
    @SerializedName("life_span")
    val lifeSpan: String?,
    val weight: Weight,
    val image: BreedImage?
)

data class Weight(
    val imperial: String,
    val metric: String
)

data class BreedImage(
    val id: String?,
    val width: Int?,
    val height: Int?,
    val url: String
)

