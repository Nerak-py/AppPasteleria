package com.example.appevalaucion.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.appevalaucion.R

private val fuente = FontFamily(
    Font(R.font.lato_light, FontWeight.Light),
    Font(R.font.pacifico_regular, FontWeight.Normal))

//
// Set of Material typography styles to start with
val Typography = Typography(

    titleLarge = TextStyle (
        fontFamily = fuente,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,

    ),
    titleMedium = TextStyle(
        fontFamily = fuente,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,

    ),
    bodyLarge = TextStyle(
        fontFamily = fuente,
        fontWeight = FontWeight.W700,
        fontSize = 18.sp,
    )

)
