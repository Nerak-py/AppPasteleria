package com.example.appevalaucion.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.material3.SnackbarHostState
import com.example.appevalaucion.navigate.AppRoutes
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompraScreen(navController: NavController) {

    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var numTarjeta by remember { mutableStateOf("") }
    var fechaExp by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    // Estados de error
    var nombreError by remember { mutableStateOf<String?>(null) }
    var apellidoError by remember { mutableStateOf<String?>(null) }
    var direccionError by remember { mutableStateOf<String?>(null) }
    var telefonoError by remember { mutableStateOf<String?>(null) }

    val isFormValid by remember(
        nombre, apellido, direccion, telefono,
        nombreError, apellidoError, direccionError, telefonoError
    ) {
        mutableStateOf(
            nombre.isNotBlank() &&
                    apellido.isNotBlank() &&
                    direccion.isNotBlank() &&
                    telefono.isNotBlank() &&
                    nombreError == null &&
                    apellidoError == null &&
                    direccionError == null &&
                    telefonoError == null
        )
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Finalizar Compra") },
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Informacion de envio", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    nombreError = validarNombre(it)
                },
                label = { Text("Nombre") },
                isError = nombreError != null,
                supportingText = { if (nombreError != null) Text(nombreError!!) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = apellido,
                onValueChange = {
                    apellido = it
                    apellidoError = validarNombre(it)
                },
                label = { Text("Apellido") },
                isError = apellidoError != null,
                supportingText = { if (apellidoError != null) Text(apellidoError!!) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = direccion,
                onValueChange = {
                    direccion = it
                    direccionError = validarDireccion(it)
                },
                label = { Text("Direccion de entrega") },
                isError = direccionError != null,
                supportingText = { if (direccionError != null) Text(direccionError!!) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = telefono,
                onValueChange = {
                    telefono = it.filter { c -> c.isDigit() }
                    telefonoError = validarTelefono(telefono)
                },
                label = { Text("Telefono de contacto") },
                isError = telefonoError != null,
                supportingText = { if (telefonoError != null) Text(telefonoError!!) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))

            Text("Informacion de pago", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = numTarjeta,
                onValueChange = { numTarjeta = it },
                label = { Text("Numero de tarjeta") },
                modifier = Modifier.fillMaxWidth()

            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = fechaExp,
                    onValueChange = { fechaExp = it },
                    label = { Text("Fecha de expiracion") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = cvv,
                    onValueChange = { cvv = it },
                    label = { Text("CVV") },
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {

                    nombreError = validarNombre(nombre)
                    apellidoError = validarNombre(apellido)
                    direccionError = validarDireccion(direccion)
                    telefonoError = validarTelefono(telefono)


                    if (!isFormValid) {
                        val firstError = listOfNotNull(
                            nombreError, apellidoError, direccionError, telefonoError
                        ).firstOrNull() ?: "Datos invalidos. Revisa que los datos ingresados sean correctos."
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = firstError,
                                withDismissAction = true
                            )
                        }
                        return@Button
                    }
                        scope.launch {
                            snackbarHostState.showSnackbar("Datos validados correctamente")
                            navController.navigate(AppRoutes.COMPRA_FINAL)

                        }

                },
                enabled = isFormValid,
                modifier = Modifier.fillMaxWidth(),

            ) {
                Text("Finalizar Compra")

            }
        }
    }
}


private fun validarNombre(value: String): String? {
    if (value.isBlank()) return "Campo requerido"
    val regex = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{2,}$".toRegex()
    return if (!regex.matches(value)) "Solo letras y espacios (min. 2)" else null
}

private fun validarDireccion(value: String): String? {
    if (value.isBlank()) return "Campo requerido"
    return if (value.length < 5) "Muy corta" else null
}

private fun validarTelefono(value: String): String? {
    if (value.isBlank()) return "Campo requerido"
    val regex = "^[0-9]{7,15}$".toRegex()
    return if (!regex.matches(value)) "Telefono invalido" else null
}


