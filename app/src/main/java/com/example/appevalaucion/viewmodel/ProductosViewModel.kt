package com.example.appevalaucion.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appevalaucion.model.Pastelitos
import com.example.appevalaucion.repository.PastelitosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductosViewModel : ViewModel() {
    private val repository = PastelitosRepository()

    private val _pastelitos = MutableStateFlow<List<Pastelitos>>(emptyList())
    val pastelitos: StateFlow<List<Pastelitos>> = _pastelitos.asStateFlow()

    // ✅ Método principal para recuperar pastelitos
    fun recuperarPastelitos() {
        viewModelScope.launch {
            try {
                val lista = repository.recuperarPastelitos() ?: emptyList() // ✅ Manejar null
                _pastelitos.value = lista
                Log.d("ProductosViewModel", "Productos cargados: ${lista.size}")
            } catch (e: Exception) {
                Log.e("ProductosViewModel", "Error cargando productos", e)
                _pastelitos.value = emptyList()
            }
        }
    }


    fun cargarPastelito() {
        recuperarPastelitos()
    }


    fun crearPastelito(pastelito: Pastelitos, onDone: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            try {
                val success = repository.grabarPastelito(pastelito)
                onDone(success)
                if (success) {
                    Log.d("ProductosViewModel", "Pastelito creado exitosamente")
                    recuperarPastelitos()
                }
            } catch (e: Exception) {
                Log.e("ProductosViewModel", "Error creando pastelito", e)
                onDone(false)
            }
        }
    }


    fun borrarPastelito(id: String, onDone: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            try {
                val success = repository.eliminarPastelito(id)
                onDone(success)
                if (success) {
                    Log.d("ProductosViewModel", "Pastelito eliminado exitosamente")
                    recuperarPastelitos()
                }
            } catch (e: Exception) {
                Log.e("ProductosViewModel", "Error eliminando pastelito", e)
                onDone(false)
            }
        }
    }


    fun actualizarPastelito(pastelito: Pastelitos, onDone: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            try {
                val updated = repository.updatePastelito(pastelito)
                val success = updated != null
                onDone(success)
                if (success) {
                    Log.d("ProductosViewModel", "Pastelito actualizado exitosamente")
                    recuperarPastelitos()
                }
            } catch (e: Exception) {
                Log.e("ProductosViewModel", "Error actualizando pastelito", e)
                onDone(false)
            }
        }
    }
}