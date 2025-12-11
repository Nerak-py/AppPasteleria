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
import com.example.appevalaucion.viewmodel.CarritoViewModel
import com.example.appevalaucion.viewmodel.ComprasViewModel
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompraScreen(
    navController: NavController,
    carritoViewModel: CarritoViewModel,
    comprasViewModel: ComprasViewModel
){

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
    var numTarjetaError by remember { mutableStateOf<String?>(null) }
    var fechaExpError by remember { mutableStateOf<String?>(null) }
    var cvvError by remember { mutableStateOf<String?>(null) }

    val isFormValid by remember(
        nombre, apellido, direccion, telefono, numTarjeta, fechaExp, cvv,
        nombreError, apellidoError, direccionError, telefonoError,
        numTarjetaError, fechaExpError, cvvError
    ) {
        mutableStateOf(
            nombre.isNotBlank() &&
                    apellido.isNotBlank() &&
                    direccion.isNotBlank() &&
                    telefono.isNotBlank() &&
                    numTarjeta.isNotBlank() &&
                    fechaExp.isNotBlank() &&
                    cvv.isNotBlank() &&
                    nombreError == null &&
                    apellidoError == null &&
                    direccionError == null &&
                    telefonoError == null &&
                    numTarjetaError == null &&
                    fechaExpError == null &&
                    cvvError == null
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
                    IconButton(onClick = { navController.popBackStack() }) {
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
            Text("Información de envío", style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    nombreError = validarNombre(it)
                },
                label = { Text("Nombre") },
                isError = nombreError != null,
                supportingText = {
                    nombreError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
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
                supportingText = {
                    apellidoError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = direccion,
                onValueChange = {
                    direccion = it
                    direccionError = validarDireccion(it)
                },
                label = { Text("Dirección de entrega") },
                isError = direccionError != null,
                supportingText = {
                    direccionError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = telefono,
                onValueChange = {
                    telefono = it.filter { c -> c.isDigit() }
                    telefonoError = validarTelefono(telefono)
                },
                label = { Text("Teléfono de contacto") },
                isError = telefonoError != null,
                supportingText = {
                    telefonoError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))

            Text("Información de pago", style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = numTarjeta,
                onValueChange = {
                    numTarjeta = it.filter { c -> c.isDigit() }.take(16)
                    numTarjetaError = validarNumTarjeta(numTarjeta)
                },
                label = { Text("Número de tarjeta") },
                isError = numTarjetaError != null,
                supportingText = {
                    numTarjetaError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = fechaExp,
                    onValueChange = {
                        fechaExp = formatearFechaExp(it)
                        fechaExpError = validarFechaExp(fechaExp)
                    },
                    label = { Text("MM/AA") },
                    isError = fechaExpError != null,
                    supportingText = {
                        fechaExpError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = cvv,
                    onValueChange = {
                        cvv = it.filter { c -> c.isDigit() }.take(4)
                        cvvError = validarCVV(cvv)
                    },
                    label = { Text("CVV") },
                    isError = cvvError != null,
                    supportingText = {
                        cvvError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
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
                    numTarjetaError = validarNumTarjeta(numTarjeta)
                    fechaExpError = validarFechaExp(fechaExp)
                    cvvError = validarCVV(cvv)

                    if (!isFormValid) {
                        val firstError = listOfNotNull(
                            nombreError, apellidoError, direccionError, telefonoError,
                            numTarjetaError, fechaExpError, cvvError
                        ).firstOrNull() ?: "Revisa los datos ingresados"

                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = firstError,
                                withDismissAction = true
                            )
                        }
                        return@Button
                    }

                    scope.launch {
                        comprasViewModel.registrarCompra(
                            items = carritoViewModel.carritoItems.value,
                            total = carritoViewModel.precioTotal.value,
                            nombre = nombre,
                            apellido = apellido,
                            direccion = direccion,
                            telefono = telefono
                        )
                        snackbarHostState.showSnackbar("Compra registrada exitosamente")
                        navController.navigate(AppRoutes.COMPRA_FINAL)
                    }
                },
                enabled = isFormValid,
                modifier = Modifier.fillMaxWidth()
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

private fun validarNumTarjeta(value: String): String? {
    if (value.isBlank()) return "Campo requerido"
    return when {
        value.length < 13 -> "Mínimo 13 dígitos"
        value.length > 16 -> "Máximo 16 dígitos"
        else -> null
    }
}

private fun validarFechaExp(value: String): String? {
    if (value.isBlank()) return "Campo requerido"

    val regex = "^(0[1-9]|1[0-2])/([0-9]{2})$".toRegex()
    if (!regex.matches(value)) return "Formato MM/AA"

    return null
}

private fun validarCVV(value: String): String? {
    if (value.isBlank()) return "Campo requerido"
    return when {
        value.length < 3 -> "Mínimo 3 dígitos"
        value.length > 4 -> "Máximo 4 dígitos"
        else -> null
    }
}

private fun formatearFechaExp(input: String): String {
    val digitsOnly = input.filter { it.isDigit() }.take(4)
    return when {
        digitsOnly.length <= 2 -> digitsOnly
        else -> "${digitsOnly.substring(0, 2)}/${digitsOnly.substring(2)}"
    }
}
