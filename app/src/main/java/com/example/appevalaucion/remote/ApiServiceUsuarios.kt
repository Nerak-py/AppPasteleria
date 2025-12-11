package com.example.appevalaucion.remote

import com.example.appevalaucion.model.Usuario
import retrofit2.Response
import retrofit2.http.*

interface ApiServiceUsuarios {

    @GET("usuarios/all")
    suspend fun getUsuarios(): Response<List<Usuario>>

    @GET("usuarios/{id}")
    suspend fun obtenerUsuario(@Path("id") id: Long): Response<Usuario>

    @POST("usuarios/save")
    suspend fun saveUsuarios(@Body usuario: Usuario): Response<Usuario>

    @DELETE("usuarios/delete/{id}")
    suspend fun deleteUsuarios(@Path("id") id: String): Response<Unit>

    @PUT("usuarios/update/{id}")
    suspend fun updateUsuarios(
        @Path("id") id: String,
        @Body usuario: Usuario
    ): Response<Usuario>

    @POST("usuarios/login")
    suspend fun login(@Body credentials: Map<String, String>): Response<Usuario>
}