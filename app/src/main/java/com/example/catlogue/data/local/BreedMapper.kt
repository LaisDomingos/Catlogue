package com.example.catlogue.data.mapper

import com.example.catlogue.data.local.BreedEntity
import com.example.catlogue.data.local.BreedImageEntity
import com.example.catlogue.data.local.WeightEntity
import com.example.catlogue.data.model.Breed
import com.example.catlogue.data.model.BreedImage
import com.example.catlogue.data.model.Weight

// Breed -> BreedEntity
fun Breed.toEntity(): BreedEntity {
    return BreedEntity(
        id = id,
        name = name,
        origin = origin,
        temperament = temperament,
        description = description,
        lifeSpan = lifeSpan,
        weight = WeightEntity(weight.imperial, weight.metric),
        image = image?.let {
            BreedImageEntity(it.id, it.width, it.height, it.url)
        }
    )
}

// BreedEntity -> Breed
fun BreedEntity.toBreed(): Breed {
    return Breed(
        id = id,
        name = name,
        origin = origin,
        temperament = temperament,
        description = description,
        lifeSpan = lifeSpan,
        weight = Weight(imperial = weight.imperial, metric = weight.metric),
        image = image?.let {
            BreedImage(it.id, it.width, it.height, it.url)
        }
    )
}
