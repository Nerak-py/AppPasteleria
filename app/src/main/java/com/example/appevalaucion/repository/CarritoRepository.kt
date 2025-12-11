package com.example.appevalaucion.repository

import com.example.appevalaucion.model.Carrito
import com.example.appevalaucion.model.CarritoItem
import com.example.appevalaucion.model.CarritoItemRequest
import com.example.appevalaucion.remote.RetrofitInstance


class CarritoRepository {
    private val apiService = RetrofitInstance.apiCarrito

    suspend fun getCarritoByUsuario(usuarioId: Long) =
        apiService.getCarritoByUsuario(usuarioId)

    suspend fun saveCarrito(request: Map<String, Long>) =
        apiService.saveCarrito(request)

    suspend fun getItemsByCarrito(carritoId: Long) =
        apiService.getItemsByCarritoId(carritoId)

    suspend fun saveCarritoItem(item: CarritoItemRequest) =
        apiService.saveCarritoItem(item)

    suspend fun updateCarritoItem(id: Long, item: CarritoItem) =
        apiService.updateCarritoItem(id, item)

    suspend fun deleteCarritoItem(id: Long) =
        apiService.deleteCarritoItem(id)
}
