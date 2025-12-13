package com.example.appevalaucion.model

import java.util.Date


data class Usuario(
    val id: String = "",
    val nombre: String = "",
    val apellido: String = "",
    val email: String = "",
    val contrasena: String = "",
    val telefono: String? = null,
    val region: String = "",
    val comuna: String = "",
    val fechaNacimiento: Date? = null,
    val codigoPromocional: String? = null,
    val esEstudianteDuoc: Boolean = false,
    val tieneDescuento50Anios: Boolean = false,
    val tieneDescuentoFelices50: Boolean = false,
    val fechaRegistro: Date = Date()
) {

    fun obtenerEdad(): Int {
        if (fechaNacimiento == null) return 0
        val hoy = Date()
        val diff = hoy.time - fechaNacimiento.time
        return (diff / (1000L * 60 * 60 * 24 * 365)).toInt()
    }

    // NO ESTA EN USO
    fun esMayorDe50Anios(): Boolean {
        return obtenerEdad() >= 50
    }

    // NO ESTA EN USO
    fun esEmailDuoc(): Boolean {
        return email.contains("@duoc.cl", ignoreCase = true)
    }
}
