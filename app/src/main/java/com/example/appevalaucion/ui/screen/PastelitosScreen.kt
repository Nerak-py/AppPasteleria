package com.example.appevalaucion.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appevalaucion.model.Pastelitos
import com.example.appevalaucion.viewmodel.ProductosViewModel
import androidx.compose.foundation.lazy.items
import kotlin.toString


@Composable
fun PastelitosScreen(viewModel: ProductosViewModel = viewModel()) {
    val pastelitos by viewModel.pastelitos.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {
        Text("Listado de Pasteles")
        Spacer(Modifier.height(10.dp))
        LazyColumn {
            items(pastelitos) { pastelito ->
                PastelitosItem(pastelito)
            }
        }
    }
}

@Composable
fun PastelitosItem(pastelitos: Pastelitos) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Spacer(Modifier.height(10.dp))
        Text(pastelitos.id)
        Spacer(Modifier.height(10.dp))
        Text(pastelitos.nombre)
        Spacer(Modifier.height(10.dp))
        Text("$${pastelitos.precio}")
        Divider(modifier = Modifier.height(10.dp))
    }
}
