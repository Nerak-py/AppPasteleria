package com.example.appevalaucion.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.appevalaucion.R // <-- Asegúrate de que tu logo esté aquí
import com.example.appevalaucion.navigate.AppRoutes
import com.example.appevalaucion.viewmodel.LoginState
import com.example.appevalaucion.viewmodel.LoginViewModel
import com.example.appevalaucion.viewmodel.UsuarioViewModel



private val colorChocolate = Color(0xFF6D4C41)
private val colorVainilla = Color(0xFFFFF5E1)
private val fuente = FontFamily(
    Font(R.font.lato_regular, FontWeight.Normal),
    Font(R.font.lato_bold, FontWeight.Bold)
)



@Composable
fun LoginScreen(
    usuarioViewModel: UsuarioViewModel,
    loginViewModel: LoginViewModel,
    navController: NavController
) {
    val email by loginViewModel.email.collectAsState()
    val emailError by loginViewModel.emailError.collectAsState()
    val password by loginViewModel.password.collectAsState()
    val passwordError by loginViewModel.passwordError.collectAsState()
    val isFormValid by loginViewModel.sonDatosValidos.collectAsState()
    val loginState by loginViewModel.loginState.collectAsState()

    var passwordVisible by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // ✅ LaunchedEffect corregido
    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.Success -> {
                val usuario = (loginState as LoginState.Success).usuario
                usuarioViewModel.setUsuario(usuario) // ✅ Establecer usuario
                navController.navigate(AppRoutes.BIENVENIDA) {
                    popUpTo(AppRoutes.LOGIN) { inclusive = true }
                }
                loginViewModel.resetLoginState()
            }
            is LoginState.Error -> {
                errorMessage = (loginState as LoginState.Error).message
                showErrorDialog = true
            }
            else -> {}
        }
    }

    val textFieldColors = TextFieldDefaults.colors(
        focusedIndicatorColor = colorChocolate,
        focusedLabelColor = colorChocolate,
        cursorColor = colorChocolate,
        unfocusedContainerColor = colorVainilla.copy(alpha = 0.5f),
        focusedContainerColor = colorVainilla,
    )

    if (showErrorDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = {
                showErrorDialog = false
                loginViewModel.resetLoginState()
            },
            title = { Text("Error de inicio de sesión") },
            text = { Text(errorMessage) },
            confirmButton = {
                TextButton(onClick = {
                    showErrorDialog = false
                    loginViewModel.resetLoginState()
                }) {
                    Text("Aceptar")
                }
            }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo Pastelería",
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(bottom = 24.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                "Bienvenido a Mil Sabores.",
                style = MaterialTheme.typography.headlineMedium.copy(fontFamily = fuente),
                color = colorChocolate
            )
            Text(
                "Ingresa a tu cuenta para continuar.",
                style = MaterialTheme.typography.bodyMedium.copy(fontFamily = fuente),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { loginViewModel.cambioEmail(it) },
                label = { Text("Correo Electrónico") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = emailError != null,
                supportingText = {
                    emailError?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = textFieldColors
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { loginViewModel.cambioPassword(it) },
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = passwordError != null,
                supportingText = {
                    passwordError?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },
                colors = textFieldColors,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible)
                                Icons.Filled.VisibilityOff
                            else
                                Icons.Filled.Visibility,
                            contentDescription = "Mostrar/Ocultar contraseña"
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    loginViewModel.login() // ✅ Llama al método sin parámetros
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = isFormValid && loginState !is LoginState.Loading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorChocolate,
                    disabledContainerColor = colorChocolate.copy(alpha = 0.5f)
                )
            ) {
                if (loginState is LoginState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                } else {
                    Text("INICIAR SESIÓN")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextButton(onClick = {
                    navController.navigate(AppRoutes.CREAR_USUARIO)
                }) {
                    Text("Crear Cuenta", color = colorChocolate)
                }

                TextButton(onClick = { navController.navigate(AppRoutes.AGREGAR) }) {
                    Text("¿Olvidó Contraseña?", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}