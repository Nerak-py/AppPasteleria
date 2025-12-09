package com.example.appevalaucion.ui.screen



import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appevalaucion.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaNosotros(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sobre Nosotros") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {

            Image(

                painter = painterResource(id = R.drawable.img),
                contentDescription = "Fachada de Pastelería Mil Sabores",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Crop
            )


            Text(
                text = "Pastelería Mil Sabores",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Desde 2003, endulzando cada momento.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "En Pastelería Mil Sabores, nos enorgullecemos de una tradición que se remonta a más de dos décadas. Cada uno de nuestros pasteles y postres es una obra de arte elaborada con pasión, dedicación y los ingredientes más frescos. Nuestra misión es simple: transformar cada celebración en un recuerdo inolvidable y hacer de cada día un poco más dulce para nuestros clientes.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Desde los clásicos atemporales hasta las creaciones más innovadoras, nuestro equipo de maestros pasteleros trabaja incansablemente para ofrecerte una experiencia de sabor inigualable. Ven y descubre por qué somos el lugar favorito para quienes buscan calidad, sabor y ese toque especial que solo Pastelería Mil Sabores puede ofrecer.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}