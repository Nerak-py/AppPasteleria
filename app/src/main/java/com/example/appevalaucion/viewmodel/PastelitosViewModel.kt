package com.example.appevalaucion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appevalaucion.model.Pastelitos
import com.example.appevalaucion.repository.PastelitosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PastelitoViewModel : ViewModel() {
    private val _pastelitos = MutableStateFlow<List<Pastelitos>>(emptyList())
    val pastelitos: StateFlow<List<Pastelitos>> = _pastelitos.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadPastelitos()
    }

    private fun loadPastelitos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = PastelitosRepository().recuperarPastelitos()
                _pastelitos.value = response ?: emptyList() // ✅ Manejar null
            } catch (e: Exception) {
                android.util.Log.e("PastelitoViewModel", "Error cargando pastelitos", e)
                _pastelitos.value = emptyList() // ✅ Asignar lista vacía en caso de error
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ✅ Método público para recargar pastelitos
    fun recargarPastelitos() {
        loadPastelitos()
    }
}