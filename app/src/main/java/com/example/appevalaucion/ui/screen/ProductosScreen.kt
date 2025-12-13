package com.example.appevalaucion.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appevalaucion.model.ProductoCard
import com.example.appevalaucion.model.listitaProductos
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
    isOfertas: Boolean = false
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val allProducts = remember(isOfertas) {
        if (isOfertas) listitaProductos.filter { it.precioOferta != null } else listitaProductos
    }

    val categories = remember(allProducts) {
        listOf("Todos") + allProducts.map { it.categoria }.distinct()
    }

    var selectedCategory by remember { mutableStateOf("Todos") }

    val productos = remember(allProducts, selectedCategory) {
        if (selectedCategory == "Todos") allProducts else allProducts.filter { it.categoria == selectedCategory }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isOfertas) "Ofertas Especiales" else "Nuestros Productos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
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
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(end = 8.dp)
            ) {
                items(categories) { categoria ->
                    val isSelected = selectedCategory == categoria
                    FilterChip(
                        selected = isSelected,
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

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(productos) { producto ->
                    ProductoCard(
                        producto = producto,
                        onCardClick = {
                            navController.navigate(AppRoutes.COMPRA)
                        },
                        onAddToCartClick = {
                            carritoViewModel.anadirAlCarrito(producto)
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "${producto.nombre} añadido al carrito"
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}