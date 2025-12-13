package com.example.appevalaucion.data

import com.example.appevalaucion.R
import com.example.appevalaucion.model.Noticia
import java.text.SimpleDateFormat


val noticiasDeEjemplo = listOf(
    Noticia(
        id = "1",
        titulo = "Estudiante de Duoc UC gana concurso",
        resumen = "ESTUDIANTE DE DUOC UC GANA CONCURSO QUE ELIGE A LA MEJOR TORTA DE CHOCOLATE DEL MUNDO Y OBTIENE BECA EN PRESTIGIOSA ESCUELA DE ESPAÑA",
        cuerpo = """POR PRIMERA VEZ, DUOC UC FUE INVITADO A PARTICIPAR EN LA MODALIDAD ESTUDIANTES DEL PREMIO INTERNACIONAL DE ALTA PASTELERÍA PACO TORREBLANCA... (etc.)""",
        urlImagen = R.drawable.noticias1,
        fechaPublicacion = SimpleDateFormat("dd/MM/yyyy").parse("07/08/2025")!!
    ),
    Noticia(
        id = "2",
        titulo = "Charla Chef Felipe Araya",
        resumen = "CHEF FELIPE ARAYA IMPACTA CON CHARLA SOBRE ALIMENTACIÓN, BIENESTAR Y RENDIMIENTO",
        cuerpo = """La Escuela de Gastronomía de Duoc UC dio inicio al Año Académico 2025 con una charla magistral del chef y coach Felipe Araya... (etc.)""",
        urlImagen = R.drawable.noticias2,
        fechaPublicacion = SimpleDateFormat("dd/MM/yyyy").parse("15/04/2025")!!
    )
)