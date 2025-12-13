package com.example.appevalaucion.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstanceMeal {

    private const val BASE_URL = "https://www.themealdb.com/"

    val api: ApiServiceMeal by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceMeal::class.java)
    }
}