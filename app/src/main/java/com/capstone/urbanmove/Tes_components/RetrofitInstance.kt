package com.capstone.urbanmove.Tes_components

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object RetrofitInstance {
    private const val BASE_URL = "https://urbanmove-api.onrender.com/"

    val api: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}

interface ApiService {
    @GET("api/vehicle/brands")
    suspend fun getMarcas(): List<Marca>

    @GET("api/vehicle/models/2")
    suspend fun getModelos(): List<Modelo>

    @GET("api/vehicle/colors")
    suspend fun getColor(): List<Color>
}
