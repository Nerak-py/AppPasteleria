package com.example.appevalaucion.model

import androidx.annotation.DrawableRes

data class Pastelitos(
    val id: String,
    val categoria: String,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val precioOferta: Double? = null,
    //@DrawableRes
    val image: String
)
