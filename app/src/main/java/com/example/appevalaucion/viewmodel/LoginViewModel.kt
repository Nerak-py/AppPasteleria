package com.example.appevalaucion.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appevalaucion.model.Usuario
import com.example.appevalaucion.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val repository = UsuarioRepository()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    val sonDatosValidos: StateFlow<Boolean> = combine(
        emailError,
        passwordError,
        email,
        password
    ) { emailErr, passErr, em, pass ->
        emailErr == null && passErr == null && em.isNotBlank() && pass.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun cambioEmail(nuevoEmail: String) {
        _email.value = nuevoEmail
        validarEmail(nuevoEmail)
    }

    fun cambioPassword(nuevoPassword: String) {
        _password.value = nuevoPassword
        validarPassword(nuevoPassword)
    }

    private fun validarEmail(email: String) {
        _emailError.value = when {
            email.isBlank() -> "El correo no puede estar vacío"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                "Formato de correo inválido"
            else -> null
        }
    }

    private fun validarPassword(password: String) {
        _passwordError.value = when {
            password.isBlank() -> "La contraseña no puede estar vacía"
            password.length < 6 -> "La contraseña debe tener al menos 6 caracteres"
            else -> null
        }
    }

    // ✅ Opción 1: Sin parámetros (usa los StateFlows internos)
    fun login() {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val response = repository.login(_email.value, _password.value)
                if (response.isSuccessful && response.body() != null) {
                    _loginState.value = LoginState.Success(response.body()!!)
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Credenciales inválidas"
                    Log.e("LoginViewModel", "Error login: ${response.code()} - $errorMsg")
                    _loginState.value = LoginState.Error("Credenciales inválidas")
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Excepción en login", e)
                _loginState.value = LoginState.Error("Error de conexión: ${e.message}")
            }
        }
    }

    // ✅ Opción 2: Con parámetros (más flexible)
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val response = repository.login(email, password)
                if (response.isSuccessful && response.body() != null) {
                    _loginState.value = LoginState.Success(response.body()!!)
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Credenciales inválidas"
                    Log.e("LoginViewModel", "Error login: ${response.code()} - $errorMsg")
                    _loginState.value = LoginState.Error("Credenciales inválidas")
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Excepción en login", e)
                _loginState.value = LoginState.Error("Error de conexión: ${e.message}")
            }
        }
    }

    fun resetLoginState() {
        _loginState.value = LoginState.Idle
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val usuario: Usuario) : LoginState()
    data class Error(val message: String) : LoginState()
}