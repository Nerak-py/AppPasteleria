package com.example.appevalaucion.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appevalaucion.model.Usuario
import com.example.appevalaucion.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel() {

    private val repository = UsuarioRepository()

    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario: StateFlow<Usuario?> = _usuario.asStateFlow()

    private val _usuarios = MutableStateFlow<List<Usuario>>(emptyList())
    val usuarios: StateFlow<List<Usuario>> = _usuarios.asStateFlow()

    fun setUsuario(usuario: Usuario?) {
        _usuario.value = usuario
    }

    fun recuperarUsuarios() {
        viewModelScope.launch {
            try {
                _usuarios.value = repository.recuperarUsuarios()
            } catch (e: Exception) {
                Log.e("UsuarioViewModel", "Error recuperando usuarios", e)
            }
        }
    }

    fun grabarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            try {
                val success = repository.grabarUsuario(usuario)
                if (success) recuperarUsuarios()
            } catch (e: Exception) {
                Log.e("UsuarioViewModel", "Error grabando usuario", e)
            }
        }
    }

    fun eliminarUsuario(id: String) { // ✅ Cambiado a String
        viewModelScope.launch {
            try {
                val success = repository.eliminarUsuario(id.toLongOrNull() ?: 0L)
                if (success) recuperarUsuarios()
            } catch (e: Exception) {
                Log.e("UsuarioViewModel", "Error eliminando usuario", e)
            }
        }
    }

    fun updateUsuario(usuario: Usuario) {
        viewModelScope.launch {
            try {
                val updated = repository.updateUsuario(usuario)
                if (updated != null) {
                    _usuario.value = updated
                    recuperarUsuarios()
                }
            } catch (e: Exception) {
                Log.e("UsuarioViewModel", "Error actualizando usuario", e)
            }
        }
    }

    fun registrarUsuario(nombre: String, email: String, contrasena: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val usuario = Usuario(
                    id = "", // El backend genera el ID
                    nombre = nombre,
                    apellido = "",
                    email = email,
                    contrasena = contrasena
                )
                val response = repository.register(usuario)

                if (response.isSuccessful && response.body() != null) {
                    _usuario.value = response.body() // ✅ Guardar usuario registrado
                    Log.d("UsuarioViewModel", "Usuario registrado exitosamente: ${response.body()}")
                    onResult(true)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("UsuarioViewModel", "Error al registrar: ${response.code()} - $errorBody")
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("UsuarioViewModel", "Excepción al registrar usuario", e)
                onResult(false)
            }
        }
    }

    fun cerrarSesion() {
        _usuario.value = null
    }
}