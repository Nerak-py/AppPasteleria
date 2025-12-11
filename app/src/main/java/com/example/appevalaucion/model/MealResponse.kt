package com.example.appevalaucion.model

data class MealResponse(
    val meals: List<Meal>? = null // Lista de postres
)
data class Meal(
    val idMeal: String,        // ID del postre (ej: "52855")
    val strMeal: String,       // Nombre del postre (ej: "Chocolate Gateau")
    val strMealThumb: String?  // URL de la imagen (ej: "https://www.themealdb.com/images/...")
)