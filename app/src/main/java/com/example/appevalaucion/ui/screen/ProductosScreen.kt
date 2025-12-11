package com.example.appevalaucion.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appevalaucion.model.ProductoCard
import com.example.appevalaucion.navigate.AppRoutes
import com.example.appevalaucion.viewmodel.CarritoViewModel
import com.example.appevalaucion.viewmodel.ProductosViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductosScreen(
    navController: NavController,
    productosViewModel: ProductosViewModel,
    carritoViewModel: CarritoViewModel,
    usuarioId: Long = 1L,  // ✅ Usuario por defecto para testing
    isOfertas: Boolean = false
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Estados
    val allProducts by productosViewModel.pastelitos.collectAsState()
    val carritoItems by carritoViewModel.carritoItems.collectAsState()
    val totalItems = carritoItems.sumOf { it.cantidad }

    LaunchedEffect(Unit) {
        productosViewModel.recuperarPastelitos()
        // ✅ Cargar el carrito del usuario al inicio
        if (usuarioId > 0) {
            carritoViewModel.cargarCarrito(usuarioId)
        }
    }

    // Lógica de filtrado
    val filteredProducts = remember(allProducts, isOfertas) {
        if (isOfertas) allProducts.filter { it.precioOferta != null && it.precioOferta!! > 0 }
        else allProducts
    }

    val categories = remember(filteredProducts) {
        listOf("Todos") + filteredProducts.mapNotNull { it.categoria }.distinct()
    }

    var selectedCategory by remember { mutableStateOf("Todos") }

    val productos = remember(filteredProducts, selectedCategory) {
        if (selectedCategory == "Todos") filteredProducts
        else filteredProducts.filter { it.categoria == selectedCategory }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isOfertas) "Ofertas Especiales" else "Nuestros Productos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                    }
                },
                actions = {
                    // Carrito en la barra superior
                    IconButton(onClick = { navController.navigate(AppRoutes.CARRITO) }) {
                        BadgedBox(
                            badge = {
                                if (totalItems > 0) {
                                    Badge { Text("$totalItems") }
                                }
                            }
                        ) {
                            Icon(Icons.Default.ShoppingCart, "Carrito")
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Filtros (Chips)
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(end = 8.dp)
            ) {
                items(categories) { categoria ->
                    FilterChip(
                        selected = selectedCategory == categoria,
                        onClick = { selectedCategory = categoria },
                        label = { Text(categoria) },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }

            if (productos.isEmpty() && allProducts.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (productos.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No hay productos en esta categoría",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(productos, key = { it.id }) { producto ->
                        ProductoCard(
                            producto = producto,
                            onClick = {
                                navController.navigate("${AppRoutes.COMPRA}/${producto.id}")
                            },
                            onAddToCartClick = {
                                // ✅ Validar que el usuario sea válido
                                if (usuarioId > 0) {
                                    carritoViewModel.agregarProductoAlCarrito(usuarioId, producto, 1)
                                    scope.launch {
                                        snackbarHostState.currentSnackbarData?.dismiss()
                                        snackbarHostState.showSnackbar("${producto.nombre} agregado al carrito")
                                    }
                                } else {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Debes iniciar sesión para agregar productos")
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}