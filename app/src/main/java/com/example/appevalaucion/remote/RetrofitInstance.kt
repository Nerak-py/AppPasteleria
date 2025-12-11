package com.example.appevalaucion.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:8011/api/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiPastelitos: ApiServicePastelitos by lazy {
        retrofit.create(ApiServicePastelitos::class.java)
    }

    val apiCarrito: ApiServiceCarrito by lazy {
        retrofit.create(ApiServiceCarrito::class.java)
    }


    val apiUsuario: ApiServiceUsuarios by lazy {
        retrofit.create(ApiServiceUsuarios::class.java)
    }
}
