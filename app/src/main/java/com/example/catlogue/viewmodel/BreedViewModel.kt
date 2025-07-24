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
class BreedViewModel : ViewModel() {

    // Cria uma instância do repositório, passando o serviço Retrofit pronto para uso
    private val repository = BreedRepository(RetrofitInstance.api)

    // Estado interno mutável que guarda a lista de raças (inicialmente vazia)
    private val _breeds = MutableStateFlow<List<Breed>>(emptyList())
    // Estado público imutável que a UI vai observar para receber atualizações da lista
    val breeds: StateFlow<List<Breed>> = _breeds

    // Estado interno mutável que indica se os dados estão sendo carregados
    private val _isLoading = MutableStateFlow(true)
    // Estado público imutável para a UI saber quando mostrar o loading
    val isLoading: StateFlow<Boolean> = _isLoading

    // Estado interno mutável que guarda mensagem de erro, se ocorrer algum problema
    private val _error = MutableStateFlow<String?>(null)
    // Estado público imutável que a UI pode observar para mostrar mensagem de erro
    val error: StateFlow<String?> = _error

    // Bloco init: assim que a ViewModel é criada, já inicia a busca das raças
    init {
        fetchBreeds()
    }

    // Função que faz a busca dos dados de forma assíncrona dentro do ViewModelScope
    private fun fetchBreeds() {
        viewModelScope.launch {
            _isLoading.value = true      // Indica que começou o carregamento
            _error.value = null          // Limpa erro antigo, se existir

            try {
                // Chama o repositório para buscar a lista de raças da API
                val result = repository.getBreeds()
                _breeds.value = result  // Atualiza o estado com os dados recebidos
            } catch (e: Exception) {
                // Se der erro, guarda a mensagem para mostrar na UI
                _error.value = e.message
            } finally {
                // Finaliza o carregamento, seja sucesso ou erro
                _isLoading.value = false
            }
        }
    }
}