package com.example.catlogue.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val API_KEY = "live_QelljQyH5LA7WUvhIBPrnkOpI0Iwuky0Lu6c28IqNmlHmZ8jyZWxkzoSTjPIhBdj"

    private val client = OkHttpClient.Builder()
        .addInterceptor(Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("x-api-key", API_KEY)
                .build()
            chain.proceed(request)
        })
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .client(client)  // adiciona o cliente com o interceptor
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: CatApiService by lazy {
        retrofit.create(CatApiService::class.java)
    }
}
