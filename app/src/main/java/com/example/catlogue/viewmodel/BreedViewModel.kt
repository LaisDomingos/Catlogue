package com.example.catlogue.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.util.Log
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
import kotlinx.coroutines.withContext

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

    private val _isLoading = MutableStateFlow(false)  // <--- Loading público
    val isLoading: StateFlow<Boolean> = _isLoading

    private var currentPage = 0
    private var endReached = false
    private val limitPerPage = 10

    init {
        loadFavorites()
        loadMoreBreeds()
    }

    fun loadMoreBreeds() {
        if (_isLoading.value || endReached) return

        viewModelScope.launch {
            _isLoading.value = true
            val newBreeds = repository.getBreedsPaginated(limitPerPage, currentPage)
            if (newBreeds.isEmpty()) {
                endReached = true
            } else {
                _breeds.value = _breeds.value + newBreeds
                currentPage++
            }
            _isLoading.value = false
        }
    }

    fun loadFirstPage() {
        viewModelScope.launch {
            val breeds = repository.getBreedsPaginated(limit = 10, page = 0)
            Log.d("GATINHOS", "Página 1: ${breeds.size} raças")
            breeds.forEach {
                Log.d("GATINHO", it.name)
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
