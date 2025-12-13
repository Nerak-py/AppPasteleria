
package com.example.appevalaucion.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appevalaucion.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date

class UsuarioViewModel : ViewModel() {
    private val _usuario = MutableStateFlow<Usuario?>(null)

    val usuario: StateFlow<Usuario?> = _usuario.asStateFlow()


    fun registrarUsuario(nombre: String, email: String, contrasena: String) {

        val nuevoUsuario = Usuario(
            id = Date().time.toString(),
            nombre = nombre,
            email = email,
            contrasena = contrasena
        )

        // Después de registrar, iniciamos sesión automáticamente
        _usuario.value = nuevoUsuario
    }



    // NO ESTA EN USO
    fun login(email: String, contrasena: String) {

        if (email.isNotEmpty() && contrasena.isNotEmpty()) {
            _usuario.value = Usuario(
                id = "12345",
                nombre = "Pablo",
                apellido = "Marmol",
                email = email,
                region = "Metropolitana",
                comuna = "Pastelillo",
                esEstudianteDuoc = email.endsWith("@duoc.cl")
            )
        }
    }
    // NO ESTA EN USO
    fun logout() {
        _usuario.value = null
    }
}