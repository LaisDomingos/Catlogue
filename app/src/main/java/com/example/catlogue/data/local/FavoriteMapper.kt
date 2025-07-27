package com.example.catlogue.data.local

import com.example.catlogue.data.model.Breed
import com.example.catlogue.data.model.BreedImage
import com.example.catlogue.data.model.Weight

fun Breed.toFavoriteEntity(): FavoriteEntity {
    return FavoriteEntity(
        breedId = this.id,
        name = this.name,
        imageUrl = this.image?.url,
        lifeSpan = this.lifeSpan,
        origin = this.origin,
        temperament = this.temperament,
        description = this.description
    )
}


fun FavoriteEntity.toBreed(): Breed {
    return Breed(
        id = this.breedId,
        name = this.name,
        origin = this.origin ?: "",
        temperament = this.temperament ?: "",
        description = this.description ?: "",
        lifeSpan = this.lifeSpan,
        weight = Weight("", ""),
        image = this.imageUrl?.let { url ->
            BreedImage(id = null, width = null, height = null, url = url)
        }
    )
}

