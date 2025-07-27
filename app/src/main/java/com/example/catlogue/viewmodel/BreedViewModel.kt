package com.example.catlogue.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catlogue.data.local.FavoriteDao
import com.example.catlogue.data.local.toFavoriteEntity
import com.example.catlogue.data.local.FavoriteEntity
import com.example.catlogue.data.model.Breed
import com.example.catlogue.data.repository.BreedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.catlogue.data.local.toBreed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext // ⬅️ importante pra chamar o callback no Main thread

class BreedViewModel(
    private val repository: BreedRepository,
    private val favoriteDao: FavoriteDao
) : ViewModel() {

    // StateFlows
    private val _breeds = MutableStateFlow<List<Breed>>(emptyList())
    val breeds: StateFlow<List<Breed>> = _breeds

    private val _favoriteBreeds = MutableStateFlow<List<Breed>>(emptyList())
    val favoriteBreeds: StateFlow<List<Breed>> = _favoriteBreeds

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadBreeds()
        loadFavorites()
        fetchBreeds()
    }

    private fun fetchBreeds() {
        viewModelScope.launch {
            _error.value = null
            try {
                val result = repository.getBreeds()
                _breeds.value = result

                println("🐾 Raças no banco local: ${result.size}")
                result.forEach {
                    println("-> ${it.name} (${it.origin}) - (${it.lifeSpan})")
                }

            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    private fun loadBreeds() {
        viewModelScope.launch {
            try {
                val result = repository.getBreeds()
                _breeds.value = result
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun addFavorite(breed: Breed) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                favoriteDao.addFavorite(breed.toFavoriteEntity())
                println("DEBUG: Adicionado ${breed.name} aos favoritos")
                loadFavorites() // atualiza a lista se necessário
            } catch (e: Exception) {
                println("ERRO: Falha ao adicionar ${breed.name} - ${e.message}")
            }
        }
    }

    fun removeFavorite(breed: Breed) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                favoriteDao.removeFavorite(breed.id)
                println("DEBUG: Removido ${breed.name} dos favoritos")
                loadFavorites()
            } catch (e: Exception) {
                println("ERRO: Falha ao remover ${breed.name} - ${e.message}")
            }
        }
    }

    // Esse método chama o callback no Main thread (essencial pro Compose)
    fun isFavorite(breedId: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = favoriteDao.isFavorite(breedId)
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    fun loadFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val list = favoriteDao.getAllFavorites()
                val mapped = list.map { it.toBreed() }

                withContext(Dispatchers.Main) {
                    _favoriteBreeds.value = mapped
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = e.message
                }
            }
        }
    }
}
