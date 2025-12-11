package com.example.appevalaucion.model


data class PerfilErrores(
    val nombre: String? = null,
    val nombreUsuario: String? = null,
    val email: String? = null,
    val apellido: String? = null,
    val contrasena: String? = null
)

data class PerfilState(
    val usuario: Usuario = Usuario(
        nombre = "",
        email = "",
        contrasena = ""
    ),
    val error: PerfilErrores = PerfilErrores()
)