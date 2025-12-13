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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import com.example.appevalaucion.R // <-- Asegúrate de que tu logo esté aquí
import com.example.appevalaucion.navigate.AppRoutes
import com.example.appevalaucion.viewmodel.UsuarioViewModel



private val colorChocolate = Color(0xFF6D4C41)
private val colorVainilla = Color(0xFFFFF5E1)
private val fuente = FontFamily(
    Font(R.font.lato_regular, FontWeight.Normal),
    Font(R.font.lato_bold, FontWeight.Bold)
)



@Composable
fun LoginScreen(viewModel: UsuarioViewModel, navController: NavController) {

    // Estados para los campos de texto
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Colores personalizados para los TextField
    val textFieldColors = TextFieldDefaults.colors(
        focusedIndicatorColor = colorChocolate,
        focusedLabelColor = colorChocolate,
        cursorColor = colorChocolate,
        unfocusedContainerColor = colorVainilla.copy(alpha = 0.5f),
        focusedContainerColor = colorVainilla,
    )

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

            // Logo de la Pastelería
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo Pastelería",
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(bottom = 24.dp),
                contentScale = ContentScale.Fit
            )

            // Título
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

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo Electrónico") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = textFieldColors
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = textFieldColors,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                // Lógica para ocultar/mostrar contraseña
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.VisibilityOff
                    else
                        Icons.Filled.Visibility

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = "Mostrar/Ocultar contraseña")
                    }
                }
            )
            Spacer(modifier = Modifier.height(32.dp))

            // Iniciar Sesión
            Button(
                onClick = {
                    navController.navigate(AppRoutes.BIENVENIDA) {
                        popUpTo(AppRoutes.LOGIN) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorChocolate // Color temático
                )
            ) {
                Text("INICIAR SESIÓN")
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Crear cuenta / Olvido contraseña
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextButton(onClick = {
                    // dirige a de crear usuario
                    navController.navigate(AppRoutes.CREAR_USUARIO)
                }) {
                    Text("Crear Cuenta", color = colorChocolate)
                }

                TextButton(onClick = {navController.navigate(AppRoutes.AGREGAR)} ) {
                    Text("¿Olvidó Contraseña?", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}