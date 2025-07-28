package com.example.catlogue.data.remote

import com.example.catlogue.data.model.Breed
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiService {

    @GET("breeds")
    suspend fun getBreeds(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): List<Breed>
}
