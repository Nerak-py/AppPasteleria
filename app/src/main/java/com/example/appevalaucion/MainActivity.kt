package com.example.appevalaucion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.appevalaucion.components.MenuLateral
import com.example.appevalaucion.components.NavegacionInferior
import com.example.appevalaucion.components.TopBar
import com.example.appevalaucion.navigate.AppNavigate
import com.example.appevalaucion.ui.theme.AppEvaluacionTheme


class MainActivity : ComponentActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppEvaluacionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(){
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    MenuLateral(
        navController= navController,
        drawerState = drawerState
    ) {
        Contenido(
            navController = navController,
            drawerState = drawerState
            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Contenido(
    navController: NavHostController,
    drawerState: DrawerState
){
    Scaffold(
        topBar = {
            TopBar(drawerState)
        },
        bottomBar = {
            NavegacionInferior(navController)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        ){
            AppNavigate(navController = navController)
        }
    }
}