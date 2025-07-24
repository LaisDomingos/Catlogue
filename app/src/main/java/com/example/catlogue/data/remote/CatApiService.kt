// Define o pacote onde essa interface está localizada
package com.example.catlogue.data.remote

// Importa o model Breed que representa uma raça de gato
import com.example.catlogue.data.model.Breed
import retrofit2.http.GET  // Importa a anotação @GET do Retrofit para indicar requisição

// Interface que define os endpoints da API de gatos
interface CatApiService {

    // Define uma requisição HTTP GET para o endpoint "breeds"
    // Essa função, quando chamada, vai buscar a lista de raças de gato da API
    // É uma função 'suspend', ou seja, pode ser chamada de forma assíncrona (sem travar a UI)
    // Retorna uma lista de objetos Breed, que representam cada raça
    @GET("breeds")
    suspend fun getBreeds(): List<Breed>
}
