package com.example.appevalaucion.ui.screen


import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.appevalaucion.navigate.AppRoutes

@Composable
fun PantallaContacto(
    navController: NavController
) {
    val context = LocalContext.current
    // solicitar el permiso de ubicación
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                // El usuario acepta el permiso.
                Toast.makeText(context, "¡Permiso aceptado! Ahora podemos mostrar tu ubicación.", Toast.LENGTH_SHORT).show()
            } else {
                // El usuario niega el permiso.
                Toast.makeText(context, "Permiso denegado. No podemos mostrar tu ubicación.", Toast.LENGTH_SHORT).show()
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Contáctanos",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Información de contacto ficticia
        Text("Teléfono: +56 2 2555 1234")
        Text("Correo: hola@pasteleriamilsabores.cl")

        Spacer(modifier = Modifier.height(32.dp))

        // Placeholder para el mapa
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(Color.LightGray)
                .clickable {
                    // Comprueba si el permiso ya fue concedido
                    when (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        PackageManager.PERMISSION_GRANTED -> {
                            Toast.makeText(context, "Permiso concedido. Mostrando ubicación...", Toast.LENGTH_SHORT).show()
                            // redirige al mapa
                            navController.navigate(AppRoutes.MAPA)
                        }
                        else -> {
                            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Toca aquí para ver el mapa\ny solicitar la ubicación",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}
