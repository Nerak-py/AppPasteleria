package com.example.appevalaucion.model

// DTO para enviar al backend al crear/actualizar items del carrito
data class CarritoItemRequest(
    val carritoId: Long,
    val pastelitoId: String,
    val cantidad: Int
)
