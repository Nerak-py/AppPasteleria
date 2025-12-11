package com.example.appevalaucion.remote

import com.example.appevalaucion.model.MealResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiServiceMeal {

        @GET("api/json/v1/1/filter.php?c=Dessert")
        suspend fun getDessertMeals(): Response<MealResponse>
}
