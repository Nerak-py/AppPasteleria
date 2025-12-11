package com.example.appevalaucion.repository

import android.util.Log
import com.example.appevalaucion.model.Usuario
import com.example.appevalaucion.remote.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class UsuarioRepository {

    private val apiServiceUsuario = RetrofitInstance.apiUsuario

    suspend fun recuperarUsuarios(): List<Usuario> {
        return try {
            val response = apiServiceUsuario.getUsuarios()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                Log.e("UsuarioRepository", "Error al recuperar usuarios: ${response.code()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("UsuarioRepository", "Excepción al recuperar usuarios", e)
            emptyList()
        }
    }

    suspend fun grabarUsuario(u: Usuario): Boolean {
        return try {
            val response = apiServiceUsuario.saveUsuarios(u)
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("UsuarioRepository", "Error al grabar usuario", e)
            false
        }
    }

    suspend fun eliminarUsuario(id: Long): Boolean {
        return try {
            val response = apiServiceUsuario.deleteUsuarios(id.toString())
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("UsuarioRepository", "Error al eliminar usuario", e)
            false
        }
    }

    suspend fun updateUsuario(usuario: Usuario): Usuario? {
        return try {
            val id = usuario.id ?: return null
            val response = apiServiceUsuario.updateUsuarios(id = id.toString(), usuario = usuario)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            Log.e("UsuarioRepository", "Error al actualizar usuario", e)
            null
        }
    }

    suspend fun login(email: String, password: String): Response<Usuario> {
        return try {
            apiServiceUsuario.login(mapOf("email" to email, "contrasena" to password))
        } catch (e: Exception) {
            Log.e("UsuarioRepository", "Error en login", e)
            throw e
        }
    }

    suspend fun register(usuario: Usuario): Response<Usuario> {
        return try {
            apiServiceUsuario.saveUsuarios(usuario)
        } catch (e: Exception) {
            Log.e("UsuarioRepository", "Error al registrar usuario", e)
            throw e
        }
    }

    suspend fun obtenerUsuarioPorId(usuarioId: Long): Usuario? {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiServiceUsuario.obtenerUsuario(usuarioId)
                if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }
    }
}