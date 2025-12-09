package com.example.appevalaucion.remote

import com.example.appevalaucion.model.Pastelitos
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("/api/productos/all")
    suspend fun getPastelitos(): Response<List<Pastelitos>>


    @POST("/api/productos/save")
    suspend fun savePastelito(@Body pastelito: Pastelitos): Response<Pastelitos>

    @DELETE("/api/productos/delete/{id}")
    suspend fun deletePastelito(@Path ("id")id: String): Response<Unit>

    @GET("/api/productos/find/{id}")
    suspend fun findPastelito(@Path ("id")id: String): Response<Pastelitos>

    @PUT("productos/update/{id}")
    suspend fun updatePastelito(@Path ("id")id: String, @Body pastelitos: Pastelitos): Response<Pastelitos>
}

