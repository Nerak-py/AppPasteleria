package com.example.appevalaucion.model

import java.util.Date

// DATA CLAS PARA representar las noticias
data class Noticia(
    val id: String = "",
    val titulo: String = "",
    val resumen: String = "",
    val cuerpo: String = "",
    val urlImagen: Int,
    val fechaPublicacion: Date = Date()
)
