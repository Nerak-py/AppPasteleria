package com.example.appevalaucion.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.appevalaucion.model.Pastelitos
import com.example.appevalaucion.ui.screen.CarritoItems

class CarritoViewModel: ViewModel(){

    private val _carritoItems = mutableStateListOf<CarritoItems>()
    val carritoItems: List<CarritoItems> = _carritoItems
    fun anadirAlCarrito(producto: Pastelitos){
        val itemExistente = _carritoItems.find { it.producto.id == producto.id }

        if (itemExistente != null) {
            val index = _carritoItems.indexOf(itemExistente)
            _carritoItems[index] = itemExistente.copy(cantidad = itemExistente.cantidad + 1)
        } else {
            _carritoItems.add(CarritoItems(producto, 1))
        }
    }
    fun sumarCantidad(item: CarritoItems) {
        val index = _carritoItems.indexOf(item)
        if (index != -1) {
            _carritoItems[index] = item.copy(cantidad = item.cantidad + 1)
        }
    }
    fun restarCantidad(item: CarritoItems) {
        val index = _carritoItems.indexOf(item)
        if (index != -1) {
            if (item.cantidad > 1) {
                _carritoItems[index] = item.copy(cantidad = item.cantidad - 1)
            } else {
                _carritoItems.removeAt(index)
            }
        }
    }

    fun eliminarDelCarrito(item: CarritoItems) {
        _carritoItems.remove(item)
    }

    fun obtenerPrecioTotal(): Double {
        return _carritoItems.sumOf {
            val precio = it.producto.precioOferta ?: it.producto.precio
            precio * it.cantidad
        }
    }
}
