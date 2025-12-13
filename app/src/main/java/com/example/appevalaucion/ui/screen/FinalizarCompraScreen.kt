package com.example.appevalaucion.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appevalaucion.R
import com.example.appevalaucion.navigate.AppRoutes



private val colorChocolate = Color(0xFF6D4C41)
private val colorVainilla = Color(0xFFFFF5E1)


@Composable
fun FinalizarCompraScreen(navController: NavController) {

    val backgroundColor = Color(0xFFFFF5E1)
    // Colores personalizados para los TextField
    val textFieldColors = TextFieldDefaults.colors(
        focusedIndicatorColor = colorChocolate,
        focusedLabelColor = colorChocolate,
        cursorColor = colorChocolate,
        unfocusedContainerColor = colorVainilla.copy(alpha = 0.5f),
        focusedContainerColor = colorVainilla,
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.momazo),
            contentDescription = "Dificultades técnicas",
            modifier = Modifier
                .size(200.dp)
                .padding(end = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .background(backgroundColor, MaterialTheme.shapes.medium)
                .padding(20.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = "Compra Finalizada con Éxito!",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                navController.navigate(AppRoutes.HOME) {
                    popUpTo(AppRoutes.HOME) { inclusive = true }
                }
            },
            modifier = Modifier
                .height(50.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorChocolate
            )
        ) {
            Text(
                text = "Volver al Inicio",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}