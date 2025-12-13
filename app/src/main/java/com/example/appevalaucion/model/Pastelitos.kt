package com.example.appevalaucion.model

import com.google.gson.annotations.SerializedName

data class Pastelitos(
    val id: String,
    val categoria: String?,
    val nombre: String,
    val descripcion: String?,
    val precio: Double,
    @SerializedName("precioOferta")
    val precioOferta: Double?,
    val imageUrl: String?
) {
    // ✅ Helper para obtener la URL correcta según el entorno
    fun getImageUrlForDevice(): String {
        return imageUrl?.replace("localhost", "10.0.2.2") // Para emulador
            ?: "https://via.placeholder.com/300x200.png?text=${nombre}"
    }
}