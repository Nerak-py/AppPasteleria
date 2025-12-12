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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.appevalaucion.model.Pastelitos
import com.example.appevalaucion.viewmodel.CarritoViewModel
import java.text.NumberFormat
import java.util.Locale
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource


// CARRITO

data class CarritoItems(
    val producto: Pastelitos,
    val cantidad: Int
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    navController: NavController,
    carritoViewModel: CarritoViewModel = viewModel()) {
    val productos = carritoViewModel.carritoItems
    val total = carritoViewModel.obtenerPrecioTotal()

    val formatoPrecio = remember {
        NumberFormat.getCurrencyInstance(
            Locale("es", "CL")
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi carrito")
                        },
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
        bottomBar = {
            TotalCarrito(
                total = formatoPrecio.format(total),
                carritoClick = { navController.navigate("compra") },
                isEnabled = productos.isNotEmpty()
            )
        }
    ){
        paddingValues ->
        if (productos.isEmpty()){
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ){
                Text(text = "Tu carrito está vacío",
                    style = MaterialTheme.typography.titleMedium)

            }
    }else{
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(productos, key={it.producto.id}){
                item ->
                ItemCarrito(
                    item = item,
                    onSumar = {carritoViewModel.sumarCantidad(item)},
                    onRestar = {carritoViewModel.restarCantidad(item)},
                    onEliminar = {carritoViewModel.eliminarDelCarrito(item)},
                    currencyFormatter = formatoPrecio
                )
            }
        }
    }
    }
}


@Composable
private fun ItemCarrito(
    item: CarritoItems,
    onSumar: () -> Unit,
    onRestar: () -> Unit,
    onEliminar: () -> Unit,
    currencyFormatter: NumberFormat
) {
    val precio = item.producto.precioOferta ?: item.producto.precio


    OutlinedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        ListItem(
            headlineContent = { Text(item.producto.nombre) },
            supportingContent = {
                Text("Precio: ${currencyFormatter.format(precio)}")
            },
            leadingContent = {
                Image(
                    painter = painterResource(id = item.producto.image),
                    contentDescription = item.producto.nombre,
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
            modifier = Modifier.fillMaxWidth().padding(16.dp, 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onRestar) {
                Icon(Icons.Default.Remove, contentDescription = "Quitar Producto")
            }
            Text("${item.cantidad}", style = MaterialTheme.typography.titleMedium)
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

){
    Surface(
        modifier = Modifier.fillMaxWidth().navigationBarsPadding(),
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
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











