package com.example.catlogue.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catlogue.data.remote.RetrofitInstance
import com.example.catlogue.data.repository.BreedRepository
import com.example.catlogue.data.model.Breed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ViewModel que controla o estado da lista de raças de gatos e gerencia a lógica de busca
class BreedViewModel(
    private val repository: BreedRepository
) : ViewModel() {

    private val _breeds = MutableStateFlow<List<Breed>>(emptyList())
    val breeds: StateFlow<List<Breed>> = _breeds

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchBreeds()
    }

    private fun fetchBreeds() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val result = repository.getBreeds()
                _breeds.value = result

                // Aqui imprime no Logcat todas as raças carregadas
                println("🐾 Raças no banco local: ${result.size}")
                result.forEach {
                    println("-> ${it.name} (${it.origin}) - (${it.lifeSpan})")
                }

            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

}

