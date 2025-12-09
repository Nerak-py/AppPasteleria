package com.example.appevalaucion.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appevalaucion.model.Pastelitos
import com.example.appevalaucion.model.listitaProductos
import com.example.appevalaucion.repository.PastelitosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductosViewModel: ViewModel(){

    private val repository= PastelitosRepository()
    private val _productos = MutableStateFlow<List<Pastelitos>>(emptyList())
    val productos: StateFlow<List<Pastelitos>> = _productos.asStateFlow()

    private val _ofertas = MutableStateFlow<List<Pastelitos>>(emptyList())
    val ofertas: StateFlow<List<Pastelitos>> = _ofertas.asStateFlow()

    private val _mensajeError = MutableStateFlow<String?>(null)
    val mensajeError = _mensajeError.asStateFlow()

    init {
        loadProductos()
    }

    private fun loadProductos() {
        viewModelScope.launch {
            try {
                val data = repository.recuperarPastelitos()
                if (data != null && data.isNotEmpty()) {
                    // ... tu lógica de éxito ...
                    _mensajeError.value = null // Limpiar error si hubo éxito
                } else {
                    _mensajeError.value = "No se encontraron productos."
                }
            } catch (e: Exception) {
                Log.e("E", "Error: ${e.message}")
                // Esto le avisa a la UI que muestre un mensaje
                _mensajeError.value = "Error de conexión. Intente nuevamente."
            }
        }
    }



}