package com.example.appevalaucion.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appevalaucion.model.Compra
import com.example.appevalaucion.model.DatosEnvio
import com.example.appevalaucion.model.EstadoCompra
import com.example.appevalaucion.model.CarritoItem // Importar desde model
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class ComprasViewModel : ViewModel() {
    private val _compras = MutableStateFlow<List<Compra>>(emptyList())
    val compras: StateFlow<List<Compra>> = _compras.asStateFlow()

    fun registrarCompra(
        items: List<CarritoItem>,
        total: Double,
        nombre: String,
        apellido: String,
        direccion: String,
        telefono: String
    ) {
        val nuevaCompra = Compra(
            id = UUID.randomUUID().toString().substring(0, 8).uppercase(),
            fecha = System.currentTimeMillis(),
            items = items,
            total = total,
            estado = EstadoCompra.COMPLETADA,
            datosEnvio = DatosEnvio(
                nombre = nombre,
                apellido = apellido,
                direccion = direccion,
                telefono = telefono
            )
        )
        _compras.value = listOf(nuevaCompra) + _compras.value
    }
}