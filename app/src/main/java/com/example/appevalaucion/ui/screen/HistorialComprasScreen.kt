package com.example.appevalaucion.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.appevalaucion.R
import com.example.appevalaucion.model.Compra
import com.example.appevalaucion.viewmodel.ComprasViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.appevalaucion.model.EstadoCompra

private val colorChocolate = Color(0xFF6D4C41)
private val colorVainilla = Color(0xFFFFF5E1)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialComprasScreen(
    navController: NavController,
    viewModel: ComprasViewModel = viewModel()
) {
    val compras by viewModel.compras.collectAsState()
    val formatoPrecio = remember {
        NumberFormat.getCurrencyInstance(Locale("es", "CL"))
    }
    val formatoFecha = remember {
        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Compras") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorChocolate,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        if (compras.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingBag,
                        contentDescription = null,
                        modifier = Modifier.size(120.dp),
                        tint = colorChocolate.copy(alpha = 0.3f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No tienes compras realizadas",
                        style = MaterialTheme.typography.titleMedium,
                        color = colorChocolate.copy(alpha = 0.7f)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(compras) { compra ->
                    CompraCard(
                        compra = compra,
                        formatoPrecio = formatoPrecio,
                        formatoFecha = formatoFecha
                    )
                }
            }
        }
    }
}

@Composable
private fun CompraCard(
    compra: Compra,
    formatoPrecio: NumberFormat,
    formatoFecha: SimpleDateFormat
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorVainilla
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Encabezado con número de orden y estado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = when (compra.estado) {
                            EstadoCompra.COMPLETADA -> Icons.Default.CheckCircle
                            EstadoCompra.EN_PROCESO -> Icons.Default.LocalShipping
                            EstadoCompra.CANCELADA -> Icons.Default.CalendarToday
                        },
                        contentDescription = null,
                        tint = when (compra.estado) {
                            EstadoCompra.COMPLETADA -> Color(0xFF4CAF50)
                            EstadoCompra.EN_PROCESO -> Color(0xFFFFA726)
                            EstadoCompra.CANCELADA -> Color(0xFFEF5350)
                        },
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Orden #${compra.id}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = colorChocolate
                    )
                }

                Text(
                    text = compra.estado.nombre,
                    style = MaterialTheme.typography.bodySmall,
                    color = when (compra.estado) {
                        EstadoCompra.COMPLETADA -> Color(0xFF4CAF50)
                        EstadoCompra.EN_PROCESO -> Color(0xFFFFA726)
                        EstadoCompra.CANCELADA -> Color(0xFFEF5350)
                    },
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Fecha
            Text(
                text = formatoFecha.format(Date(compra.fecha)),
                style = MaterialTheme.typography.bodySmall,
                color = colorChocolate.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = colorChocolate.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(12.dp))

            // Lista de productos
            Text(
                text = "Productos:",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = colorChocolate
            )

            Spacer(modifier = Modifier.height(8.dp))

            compra.items.forEach { item ->
                val imageResId = remember(item.pastelitos.imageUrl) {
                    val id = context.resources.getIdentifier(
                        item.pastelitos.imageUrl,
                        "drawable",
                        context.packageName
                    )
                    if (id != 0) id else R.drawable.ic_launcher_foreground
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = item.pastelitos.nombre,
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color.White, RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item.pastelitos.nombre,
                            style = MaterialTheme.typography.bodyMedium,
                            color = colorChocolate,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "Cantidad: ${item.cantidad}",
                            style = MaterialTheme.typography.bodySmall,
                            color = colorChocolate.copy(alpha = 0.7f)
                        )
                    }

                    val precio = item.pastelitos.precioOferta ?: item.pastelitos.precio
                    Text(
                        text = formatoPrecio.format(precio * item.cantidad),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = colorChocolate
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = colorChocolate.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(12.dp))

            // Información de envío
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = "Información de envío",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = colorChocolate
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${compra.datosEnvio.nombre} ${compra.datosEnvio.apellido}",
                    style = MaterialTheme.typography.bodySmall,
                    color = colorChocolate
                )
                Text(
                    text = compra.datosEnvio.direccion,
                    style = MaterialTheme.typography.bodySmall,
                    color = colorChocolate
                )
                Text(
                    text = "Tel: ${compra.datosEnvio.telefono}",
                    style = MaterialTheme.typography.bodySmall,
                    color = colorChocolate
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Total
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = colorChocolate
                )
                Text(
                    text = formatoPrecio.format(compra.total),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = colorChocolate
                )
            }
        }
    }
}
