package com.example.appevalaucion.model

data class CarritoItem(
    val id: Long? = null,
    val carrito: Carrito,
    val pastelitos: Pastelitos,
    val cantidad: Int
)