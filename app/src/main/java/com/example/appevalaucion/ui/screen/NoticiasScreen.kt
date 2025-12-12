package com.example.appevalaucion.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appevalaucion.data.noticiasDeEjemplo
import com.example.appevalaucion.model.Noticia
import com.example.appevalaucion.navigate.AppRoutes
import java.text.SimpleDateFormat
import java.util.*


private val lightPink = Color(0xFFFFE8E8)
private val brown = Color(0xFF6D4C41)

@Composable
fun PantallaNoticias(navController: NavController) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Text(
                text = "Noticias",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        itemsIndexed(noticiasDeEjemplo) { index, noticia ->
            NoticiaCard(noticia = noticia, navController = navController, isReversed = index % 2 != 0)
        }
    }
}

@Composable
fun NoticiaCard(noticia: Noticia, navController: NavController, isReversed: Boolean) {
    val cardColor = if (isReversed) lightPink else Color(0xFFFFF5E1)
    val buttonColor = if (isReversed) brown else Color(0xFFE91E63)

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val content = @Composable { modifier: Modifier ->
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "PUBLICADO EL ${SimpleDateFormat("dd MMMM, yyyy", Locale("es", "ES")).format(noticia.fechaPublicacion)}".uppercase(),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = cardColor),
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "\"${noticia.resumen}\"",
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = { navController.navigate(AppRoutes.createNoticiaDetalleRoute(noticia.id)) },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                ) {
                    Text("VER NOTICIA")
                }
            }
        }

        val image = @Composable { modifier: Modifier ->
            Image(
                painter = painterResource(id = noticia.urlImagen),
                contentDescription = "Imagen de la noticia: ${noticia.titulo}",
                modifier = modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }

        if (isReversed) {
            image(Modifier.weight(0.4f))
            content(Modifier.weight(0.6f))
        } else {
            content(Modifier.weight(0.6f))
            image(Modifier.weight(0.4f))
        }
    }
}