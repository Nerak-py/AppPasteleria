package com.example.appevalaucion.ui.screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appevalaucion.navigate.AppRoutes
import com.example.appevalaucion.data.noticiasDeEjemplo


@Composable
fun PantallaNoticiaDetalle(navController: NavController, noticiaId: String?) {
    val noticia = noticiasDeEjemplo.find { it.id == noticiaId }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        if (noticia != null) {

            Spacer(modifier = Modifier.height(16.dp))

            // Título y Resumen del diseño
            Text(
                text = "Noticias", // Título de la sección
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "\"${noticia.resumen}\"", // El resumen de la noticia
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Cuerpo de la noticia
            Text(
                text = noticia.cuerpo,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Botones de navegación
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = { navController.popBackStack() }) {
                    Text("< Volver")
                }

                val currentIndex = noticiasDeEjemplo.indexOf(noticia)
                val nextNoticia = noticiasDeEjemplo.getOrNull(currentIndex + 1)
                if (nextNoticia != null) {
                    TextButton(onClick = {
                        navController.navigate(AppRoutes.createNoticiaDetalleRoute(nextNoticia.id)) {
                            popUpTo(AppRoutes.createNoticiaDetalleRoute(noticia.id)) { inclusive = true }
                        }
                    }) {
                        Text("Siguiente >")
                    }
                }
            }
        } else {
            Text(text = "No se encontró la noticia.")
        }
    }
}
