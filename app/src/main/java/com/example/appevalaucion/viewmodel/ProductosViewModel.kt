package com.example.appevalaucion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appevalaucion.model.Pastelitos
import com.example.appevalaucion.model.listitaProductos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductosViewModel: ViewModel(){

    private val _productos = MutableStateFlow<List<Pastelitos>>(emptyList())
    val productos: StateFlow<List<Pastelitos>> = _productos.asStateFlow()

    private val _ofertas = MutableStateFlow<List<Pastelitos>>(emptyList())
    val ofertas: StateFlow<List<Pastelitos>> = _ofertas.asStateFlow()

    init {
        loadProductos()
    }

    private fun loadProductos() {
        viewModelScope.launch {
            _productos.value = listitaProductos
            _ofertas.value = listitaProductos.filter { it.precioOferta != null }
        }
    }



}