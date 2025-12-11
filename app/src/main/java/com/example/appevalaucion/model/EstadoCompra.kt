package com.example.appevalaucion.model

import com.example.appevalaucion.model.CarritoItem

enum class EstadoCompra(val nombre: String) {
    COMPLETADA("Completada"),
    EN_PROCESO("En Proceso"),
    CANCELADA("Cancelada")
}

data class DatosEnvio(
    val nombre: String,
    val apellido: String,
    val direccion: String,
    val telefono: String
)

data class Compra(
    val id: String,
    val fecha: Long,
    val items: List<CarritoItem>,
    val total: Double,
    val estado: EstadoCompra,
    val datosEnvio: DatosEnvio
)
