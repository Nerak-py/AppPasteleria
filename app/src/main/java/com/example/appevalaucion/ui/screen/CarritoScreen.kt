package com.example.appevalaucion.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.appevalaucion.model.CarritoItem
import com.example.appevalaucion.viewmodel.CarritoViewModel
import java.text.NumberFormat
import java.util.Locale
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.LocalContext
import com.example.appevalaucion.R
import kotlinx.coroutines.launch
import kotlin.compareTo
import kotlin.text.format
import kotlin.times


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    navController: NavController,
    carritoViewModel: CarritoViewModel = viewModel(),
    usuarioId: Long
) {
    val pastelitos by carritoViewModel.carritoItems.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // ✅ Cargar el carrito cuando se monta la pantalla
    LaunchedEffect(usuarioId) {
        carritoViewModel.cargarCarrito(usuarioId)
    }

    // ✅ Observar cambios en los items y mostrar mensaje
    LaunchedEffect(pastelitos.size) {
        android.util.Log.d("CarritoScreen", "Items actualizados: ${pastelitos.size}")
        pastelitos.forEach { item ->
            android.util.Log.d("CarritoScreen", "Item: ${item.pastelitos?.nombre}, cantidad: ${item.cantidad}")
        }
    }

    val total = pastelitos.sumOf {
        it.pastelitos?.let { producto ->
            val precio = producto.precioOferta?.takeIf { oferta -> oferta > 0.0 } ?: producto.precio
            precio * (it.cantidad ?: 0)
        } ?: 0.0
    }

    val formatoPrecio = remember {
        NumberFormat.getCurrencyInstance(Locale("es", "CL"))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi carrito (${pastelitos.size})") }, // ✅ Mostrar cantidad
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }, // ✅ Agregar SnackbarHost
        bottomBar = {
            TotalCarrito(
                total = formatoPrecio.format(total),
                carritoClick = { navController.navigate("compra") },
                isEnabled = pastelitos.isNotEmpty()
            )
        }
    ) { paddingValues ->
        if (pastelitos.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tu carrito está vacío",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(pastelitos, key = { it.id ?: 0 }) { item ->
                    ItemCarrito(
                        item = item,
                        onSumar = {
                            item.id?.let { id ->
                                carritoViewModel.actualizarCantidad(
                                    id,
                                    (item.cantidad ?: 0) + 1
                                )
                                scope.launch{
                                    snackbarHostState.showSnackbar("Cantidad actualizada")
                                }
                            }
                        },
                        onRestar = {
                            if ((item.cantidad ?: 0) > 1) {
                                item.id?.let { id ->
                                    carritoViewModel.actualizarCantidad(
                                        id,
                                        (item.cantidad ?: 0) - 1
                                    )
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Cantidad actualizada")
                                    }
                                }
                            }
                        },
                        onEliminar = {
                            item.id?.let { id ->
                                carritoViewModel.eliminarItem(id)
                                scope.launch {
                                    snackbarHostState.showSnackbar("Producto eliminado")
                                }
                            }
                        },
                        currencyFormatter = formatoPrecio
                    )
                }
            }
        }
    }
}



@Composable
private fun ItemCarrito(
    item: CarritoItem,
    onSumar: () -> Unit,
    onRestar: () -> Unit,
    onEliminar: () -> Unit,
    currencyFormatter: NumberFormat
) {
    // ✅ Validar que pastelitos no sea null
    val pastelito = item.pastelitos ?: return

    val precio = pastelito.precioOferta?.takeIf { it > 0.0 } ?: pastelito.precio

    val context = LocalContext.current
    val imageResId = remember(pastelito.imageUrl) {
        val id = context.resources.getIdentifier(
            pastelito.imageUrl,
            "drawable",
            context.packageName
        )
        if (id != 0) id else R.drawable.ic_launcher_foreground
    }

    OutlinedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        ListItem(
            headlineContent = { Text(pastelito.nombre) },
            supportingContent = {
                Text("Precio: ${currencyFormatter.format(precio)}")
            },
            leadingContent = {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = pastelito.nombre,
                    modifier = Modifier.size(64.dp),
                    contentScale = ContentScale.Crop
                )
            },
            trailingContent = {
                IconButton(onClick = onEliminar) {
                    Icon(Icons.Default.Delete, contentDescription = "Remover Producto")
                }
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onRestar) {
                Icon(Icons.Default.Remove, contentDescription = "Quitar Producto")
            }
            Text("${item.cantidad ?: 0}", style = MaterialTheme.typography.titleMedium)
            IconButton(onClick = onSumar) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Producto")
            }
        }
    }
}

@Composable
private fun TotalCarrito(
    total: String,
    carritoClick: () -> Unit,
    isEnabled: Boolean
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total a pagar: ", style = MaterialTheme.typography.titleMedium)
                Text(total, style = MaterialTheme.typography.titleLarge)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = carritoClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = isEnabled
            ) {
                Text("Proceder al Pago")
            }
        }
    }
}
