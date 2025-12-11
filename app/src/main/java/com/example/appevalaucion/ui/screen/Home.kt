package com.example.appevalaucion.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.appevalaucion.R
import com.example.appevalaucion.model.Pastelitos
import com.example.appevalaucion.viewmodel.ProductosViewModel
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale
import kotlin.compareTo
import kotlin.text.format

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navController: NavController,
    productosViewModel: ProductosViewModel // ✅ Agregar ViewModel
) {
    // ✅ Cargar productos al iniciar
    LaunchedEffect(Unit) {
        productosViewModel.recuperarPastelitos()
    }

    // ✅ Observar lista de productos desde el ViewModel
    val productos by productosViewModel.pastelitos.collectAsState()

    // ✅ Filtrar ofertas
    val ofertas = remember(productos) {
        productos.filter { it.precioOferta != null && it.precioOferta!! > 0 }
    }

    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Ofertas del día
            item {
                Seccion(
                    title = "Ofertas del día",
                    onSeeAllClick = { navController.navigate("ofertas") }
                ) {
                    if (ofertas.isEmpty()) {
                        Text(
                            text = "No hay ofertas disponibles",
                            modifier = Modifier.padding(16.dp)
                        )
                    } else {
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            items(ofertas, key = { it.id }) { producto -> // ✅ key única
                                ProductCardSmall(
                                    producto = producto,
                                    onClick = { navController.navigate("ofertas") }
                                )
                            }
                        }
                    }
                }
            }

            // Productos destacados
            item {
                Seccion(
                    title = "Productos Destacados",
                    onSeeAllClick = { navController.navigate("productos") }
                ) {
                    if (productos.isEmpty()) {
                        Text(
                            text = "No hay productos disponibles",
                            modifier = Modifier.padding(16.dp)
                        )
                    } else {
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            items(productos, key = { it.id }) { producto -> // ✅ key única
                                ProductCardSmall(
                                    producto = producto,
                                    onClick = { navController.navigate("productos") }
                                )
                            }
                        }
                    }
                }
            }

            // Blog
            item {
                Seccion(
                    title = "Blog Y Noticias",
                    onSeeAllClick = { navController.navigate("blog") }
                ) {
                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { navController.navigate("blog") }
                    ) {
                        ListItem(
                            headlineContent = { Text("Pastel de temporada") },
                            supportingContent = {
                                Text("Descubre nuevas recetas, noticias y tips de repostería artesanal.")
                            },
                            trailingContent = {
                                Icon(Icons.Default.Book, contentDescription = null)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Seccion(
    title: String,
    onSeeAllClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            TextButton(onClick = onSeeAllClick) {
                Text("Ver todo")
            }
        }
        content()
    }
}

@Composable
private fun ProductCardSmall(producto: Pastelitos, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.size(width = 160.dp, height = 220.dp)
    ) {
        val formatoPrecio = remember {
            NumberFormat.getCurrencyInstance(Locale("es", "CL")).apply {
                currency = Currency.getInstance("CLP")
                maximumFractionDigits = 0
            }
        }

        val context = LocalContext.current
        val imageResId = remember(producto.imageUrl) {
            // ✅ Manejar imageUrl nullable
            val imageName = producto.imageUrl?.takeIf { it.isNotBlank() } ?: "ic_launcher_foreground"
            val id = context.resources.getIdentifier(
                imageName,
                "drawable",
                context.packageName
            )
            if (id != 0) id else R.drawable.ic_launcher_foreground
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = imageResId,
                contentDescription = producto.nombre,
                modifier = Modifier
                    .size(100.dp)
                    .padding(top = 8.dp),
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                error = painterResource(R.drawable.ic_launcher_foreground)
            )

            Text(
                text = producto.nombre ?: "Sin nombre", // ✅ Manejar nombre nullable
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1
            )

            Text(
                text = formatoPrecio.format(producto.precio ?: 0.0), // ✅ Manejar precio nullable
                style = MaterialTheme.typography.bodyMedium,
                color = if (producto.precioOferta != null)
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                else
                    MaterialTheme.colorScheme.primary
            )

            if (producto.precioOferta != null && producto.precioOferta!! > 0) {
                Text(
                    text = formatoPrecio.format(producto.precioOferta!!),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "¡Oferta!",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}
