package com.example.appevalaucion.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appevalaucion.model.Carrito
import com.example.appevalaucion.model.CarritoItem
import com.example.appevalaucion.model.CarritoItemRequest
import com.example.appevalaucion.model.Pastelitos
import com.example.appevalaucion.model.Usuario
import com.example.appevalaucion.repository.CarritoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.text.clear

class CarritoViewModel : ViewModel() {

    private val repository = CarritoRepository()

    private val _carritoItems = MutableStateFlow<List<CarritoItem>>(emptyList())
    val carritoItems: StateFlow<List<CarritoItem>> = _carritoItems

    private var carritoId: Long? = null


    val precioTotal: StateFlow<Double> = _carritoItems.map { items ->
        items.sumOf { item ->
            val precio = item.pastelitos?.precio ?: 0.0
            val cantidad = item.cantidad ?: 0
            precio * cantidad
        }
    }.stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.Lazily, 0.0)

    fun cargarCarrito(usuarioId: Long) {
        viewModelScope.launch {
            try {
                val carritoResponse = repository.getCarritoByUsuario(usuarioId)
                if (carritoResponse.isSuccessful) {
                    val carrito: Carrito? = carritoResponse.body()
                    if (carrito != null && carrito.id != null) {
                        carritoId = carrito.id
                        cargarItems(carrito.id)  // ← Llama a la función privada
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun cargarItems(carritoId: Long) {
        android.util.Log.d("CarritoViewModel", "Cargando items del carrito $carritoId")
        val itemsResponse = repository.getItemsByCarrito(carritoId)
        android.util.Log.d(
            "CarritoViewModel",
            "Response items: ${itemsResponse.isSuccessful}, code: ${itemsResponse.code()}"
        )
        if (itemsResponse.isSuccessful) {
            // ✅ Filtrar items con pastelitos null
            val items = itemsResponse.body() ?: emptyList()
            _carritoItems.value = items.filter { it.pastelitos != null }
            android.util.Log.d(
                "CarritoViewModel",
                "Items cargados: ${_carritoItems.value.size}, items con null: ${items.count { it.pastelitos == null }}"
            )
        } else {
            android.util.Log.e(
                "CarritoViewModel",
                "Error cargando items: ${itemsResponse.errorBody()?.string()}"
            )
        }
    }


    fun agregarAlCarrito(pastelitos: Pastelitos, usuarioId: Long) {
        agregarProductoAlCarrito(usuarioId, pastelitos, 1)
    }

    fun actualizarCantidad(itemId: Long, nuevaCantidad: Int) {
        viewModelScope.launch {
            val item = _carritoItems.value.find { it.id == itemId }
            item?.let {
                val itemActualizado = CarritoItem(
                    id = it.id,
                    carrito = it.carrito,
                    pastelitos = it.pastelitos,
                    cantidad = nuevaCantidad
                )
                val response = repository.updateCarritoItem(itemId, itemActualizado)
                if (response.isSuccessful) {
                    carritoId?.let { id -> cargarItems(id) }
                }
            }
        }
    }

    fun eliminarItem(itemId: Long) {
        viewModelScope.launch {
            val response = repository.deleteCarritoItem(itemId)
            if (response.isSuccessful) {
                carritoId?.let { id -> cargarItems(id) }
            }
        }
    }

    fun vaciarCarrito() {
        viewModelScope.launch {
            try {
                // Eliminar todos los items del carrito en el backend
                _carritoItems.value.forEach { item ->
                    item.id?.let { itemId ->
                        repository.deleteCarritoItem(itemId)
                    }
                }

                // Limpiar la lista local
                _carritoItems.value = emptyList()

                android.util.Log.d("CarritoViewModel", "Carrito vaciado exitosamente")
            } catch (e: Exception) {
                android.util.Log.e("CarritoViewModel", "Error al vaciar carrito", e)
            }
        }
    }


    fun agregarProductoAlCarrito(usuarioId: Long, producto: Pastelitos, cantidad: Int) {
        viewModelScope.launch {
            try {
                android.util.Log.d("CarritoViewModel", "Iniciando agregar producto. UserId: $usuarioId, Producto: ${producto.nombre}")

                // 1. Obtener o crear carrito
                val responseCarrito = repository.getCarritoByUsuario(usuarioId)
                android.util.Log.d("CarritoViewModel", "Response carrito: ${responseCarrito.isSuccessful}, code: ${responseCarrito.code()}")

                val carrito = if (responseCarrito.isSuccessful) {
                    responseCarrito.body()
                } else {
                    // Crear nuevo carrito
                    android.util.Log.d("CarritoViewModel", "Creando nuevo carrito para usuario: $usuarioId")
                    val nuevoCarritoRequest = mapOf("usuarioId" to usuarioId)
                    val responseNuevoCarrito = repository.saveCarrito(nuevoCarritoRequest)
                    android.util.Log.d("CarritoViewModel", "Response crear carrito: ${responseNuevoCarrito.isSuccessful}, code: ${responseNuevoCarrito.code()}")

                    if (!responseNuevoCarrito.isSuccessful) {
                        android.util.Log.e("CarritoViewModel", "Error creando carrito: ${responseNuevoCarrito.errorBody()?.string()}")
                        return@launch
                    }
                    responseNuevoCarrito.body()
                }

                if (carrito == null) {
                    android.util.Log.e("CarritoViewModel", "Carrito es null, no se puede agregar item")
                    return@launch
                }

                android.util.Log.d("CarritoViewModel", "Carrito obtenido/creado con ID: ${carrito.id}")

                // 2. Crear item del carrito usando el DTO
                val itemRequest = CarritoItemRequest(
                    carritoId = carrito.id!!,
                    pastelitoId = producto.id,
                    cantidad = cantidad
                )

                android.util.Log.d("CarritoViewModel", "Enviando item: carritoId=${itemRequest.carritoId}, pastelitoId=${itemRequest.pastelitoId}, cantidad=${itemRequest.cantidad}")

                val responseItem = repository.saveCarritoItem(itemRequest)
                android.util.Log.d("CarritoViewModel", "Response item: ${responseItem.isSuccessful}, code: ${responseItem.code()}")

                if (responseItem.isSuccessful) {
                    android.util.Log.d("CarritoViewModel", "✅ Producto agregado exitosamente")
                    // Recargar items del carrito
                    cargarItems(carrito.id!!)
                } else {
                    android.util.Log.e("CarritoViewModel", "Error agregando item: ${responseItem.errorBody()?.string()}")
                }

            } catch (e: Exception) {
                android.util.Log.e("CarritoViewModel", "Error agregando producto al carrito", e)
            }
        }
    }
}