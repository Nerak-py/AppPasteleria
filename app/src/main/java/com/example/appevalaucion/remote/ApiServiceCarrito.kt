package com.example.appevalaucion.remote

import com.example.appevalaucion.model.Carrito
import com.example.appevalaucion.model.CarritoItem
import com.example.appevalaucion.model.CarritoItemRequest
import retrofit2.Response
import retrofit2.http.*

interface ApiServiceCarrito {

    @GET("carrito/usuario/{usuarioId}")
    suspend fun getCarritoByUsuario(@Path("usuarioId") usuarioId: Long): Response<Carrito>

    @POST("carrito/save")
    suspend fun saveCarrito(@Body request: Map<String, Long>): Response<Carrito>

    @GET("item-carrito/carrito/{carritoId}")
    suspend fun getItemsByCarritoId(@Path("carritoId") carritoId: Long): Response<List<CarritoItem>>

    @POST("item-carrito/save")
    suspend fun saveCarritoItem(@Body item: CarritoItemRequest): Response<CarritoItem>

    @PUT("item-carrito/update/{id}")
    suspend fun updateCarritoItem(@Path("id") id: Long, @Body item: CarritoItem): Response<CarritoItem>

    @DELETE("item-carrito/delete/{id}")
    suspend fun deleteCarritoItem(@Path("id") id: Long): Response<Void>
}
