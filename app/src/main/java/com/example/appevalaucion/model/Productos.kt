package com.example.appevalaucion.model

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

@Composable
fun ProductoCard(
    producto: Pastelitos,
    onCardClick: () -> Unit,
    onAddToCartClick: () -> Unit,
) {

    val formatoPrecio = remember {
        NumberFormat.getCurrencyInstance(
            Locale("es", "CL")).apply {
                currency = Currency.getInstance("CLP")
                maximumFractionDigits = 0
            }
    }


    Card(
        onClick = onCardClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Image(
                painter = painterResource(id = producto.image),
                contentDescription = producto.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium
                )

                if (producto.precioOferta != null) {
                    Text(
                        text = formatoPrecio.format(producto.precio),
                        style = MaterialTheme.typography.bodySmall,
                        textDecoration = TextDecoration.LineThrough
                    )
                    Text(
                        text = formatoPrecio.format(producto.precioOferta),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Text(
                        text = formatoPrecio.format(producto.precio),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Button(
                    onClick = onAddToCartClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Añadir al carrito")
                }
            }
        }
    }
}

