package com.example.catlogue.data.repository

import com.example.catlogue.data.model.Breed  // Importa o model Breed
import com.example.catlogue.data.remote.CatApiService  // Importa o serviço que conversa com a API

// Classe que vai gerenciar a obtenção dos dados das raças de gatos
// Aqui você centraliza as fontes de dados: API, banco local, cache, etc.
class BreedRepository(private val apiService: CatApiService) {

    // Função suspensa que busca a lista de raças da API usando o serviço Retrofit
    // 'suspend' porque essa função vai rodar assincronamente, sem travar a UI
    suspend fun getBreeds(): List<Breed> {
        return apiService.getBreeds() // Faz a chamada real para a API e retorna a lista
    }
}
