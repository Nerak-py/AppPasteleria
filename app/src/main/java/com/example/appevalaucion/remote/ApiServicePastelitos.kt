package com.example.appevalaucion.remote

import com.example.appevalaucion.model.Pastelitos
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServicePastelitos {
    // Listar todos los pastelitos
    @GET("/api/pastelitos")
    suspend fun getPastelitos(): Response<List<Pastelitos>>

    // Crear un nuevo pastelito
    @POST("/api/pastelitos")
    suspend fun savePastelito(@Body pastelito: Pastelitos): Response<Pastelitos>

    // Eliminar un pastelito por ID
    @DELETE("/api/pastelitos/{id}")
    suspend fun deletePastelito(@Path("id") id: String): Response<Unit>

    // Buscar un pastelito por ID
    @GET("/api/pastelitos/{id}")
    suspend fun findPastelito(@Path("id") id: String): Response<Pastelitos>

    // Actualizar un pastelito existente
    @PUT("/api/pastelitos/{id}")
    suspend fun updatePastelito(@Path("id") id: String, @Body pastelitos: Pastelitos): Response<Pastelitos>
}