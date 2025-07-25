package com.example.catlogue.data.repository

import com.example.catlogue.data.local.BreedDao
import com.example.catlogue.data.local.BreedEntity
import com.example.catlogue.data.model.Breed
import com.example.catlogue.data.remote.CatApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.catlogue.data.mapper.toEntity
import com.example.catlogue.data.mapper.toBreed

class BreedRepository(
    private val apiService: CatApiService,
    private val breedDao: BreedDao // 🧠 adicionamos o DAO
) {

    // Função principal que busca os dados, da API ou do banco local
    suspend fun getBreeds(): List<Breed> = withContext(Dispatchers.IO) {
        try {
            // 1. Tenta buscar da API
            val apiBreeds = apiService.getBreeds()

            // 2. Transforma em entidades e salva no banco
            val entities = apiBreeds.map { it.toEntity() }
            breedDao.insertBreeds(entities)

            // 3. Retorna os dados da API mesmo
            apiBreeds
        } catch (e: Exception) {
            // Se der erro (sem internet por exemplo), busca do banco local
            val localBreeds = breedDao.getAllBreeds()
            localBreeds.map { it.toBreed() } // conversão de volta
        }
    }
}
