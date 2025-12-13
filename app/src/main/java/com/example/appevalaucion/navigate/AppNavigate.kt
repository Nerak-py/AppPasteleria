package com.example.appevalaucion.navigate

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.appevalaucion.viewmodel.UsuarioViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
import com.example.appevalaucion.ui.screen.BienvenidaScreen
import com.example.appevalaucion.ui.screen.CarritoScreen
import com.example.appevalaucion.ui.screen.CompraScreen
import com.example.appevalaucion.ui.screen.Home
import com.example.appevalaucion.ui.screen.LoginScreen
import com.example.appevalaucion.ui.screen.ProductosScreen
import com.example.appevalaucion.viewmodel.ProductosViewModel
import com.example.appevalaucion.viewmodel.CarritoViewModel

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.appevalaucion.ui.screen.AgregarProducto
import com.example.appevalaucion.ui.screen.CrearUsuarioScreen
import com.example.appevalaucion.ui.screen.FinalizarCompraScreen
import com.example.appevalaucion.ui.screen.Geolocalizacion
import com.example.appevalaucion.ui.screen.PantallaContacto
import com.example.appevalaucion.ui.screen.PantallaNosotros
import com.example.appevalaucion.ui.screen.PantallaNoticiaDetalle
import com.example.appevalaucion.ui.screen.PantallaNoticias



object AppRoutes {
    const val LOGIN = "login"
    const val BIENVENIDA = "bienvenida"
    const val HOME = "home"
    const val NOSOTROS = "nosotros"
    const val BLOG = "blog"
    const val OFERTAS = "ofertas"
    const val PRODUCTOS = "productos"
    const val CARRITO = "carrito"
    const val COMPRA = "compra"

    const val COMPRA_FINAL = "compra_final"
    const val CREAR_USUARIO = "crear_usuario"
    const val CONTACTO = "contacto"
    const val MAPA = "mapa"
    const val AGREGAR = "AGREGAR"
    private const val NOTICIA_DETALLE_ROUTE = "noticia_detalle"
    const val NOTICIA_ID_ARG = "noticiaId"
    const val NOTICIA_DETALLE = "$NOTICIA_DETALLE_ROUTE/{$NOTICIA_ID_ARG}"
    fun createNoticiaDetalleRoute(noticiaId: String) = "$NOTICIA_DETALLE_ROUTE/$noticiaId"
}


@Composable
fun AppNavigate(navController: NavHostController) {
    //val navController = rememberNavController()
    val usuarioViewModel: UsuarioViewModel = viewModel()
    val productosViewModel: ProductosViewModel = viewModel()

    val carritoViewModel: CarritoViewModel = viewModel()


    NavHost(
        navController = navController,
        startDestination = AppRoutes.LOGIN //
    ) {
        composable(route = "login") {
            LoginScreen(usuarioViewModel, navController)
        }
        composable(route = "bienvenida") {
            BienvenidaScreen(usuarioViewModel, navController)
        }

        composable(route = "home") {
            Home(navController)
        }

        composable(route = "ofertas") {
            ProductosScreen(
                navController = navController,
                productosViewModel = productosViewModel, // Para mostrar productos
                carritoViewModel = carritoViewModel,      // Para "Añadir al carrito"
                isOfertas = true
            )
        }

        composable(route = "productos") {
            ProductosScreen(
                navController = navController,
                productosViewModel = productosViewModel, // Para mostrar productos
                carritoViewModel = carritoViewModel,      // Para "Añadir al carrito"
                isOfertas = false
            )
        }

        composable(route = "carrito") {
            CarritoScreen(
                navController = navController,
                carritoViewModel = carritoViewModel
            )
        }

        composable(route = "compra") {
            CompraScreen(navController)

        }



        composable(route = AppRoutes.BLOG) {
            PantallaNoticias(navController = navController)
        }


        composable(
            route = AppRoutes.NOTICIA_DETALLE,

            arguments = listOf(navArgument(AppRoutes.NOTICIA_ID_ARG) {
                type = NavType.StringType
            })
        ) { backStackEntry ->

            val noticiaId = backStackEntry.arguments?.getString(AppRoutes.NOTICIA_ID_ARG)
            PantallaNoticiaDetalle(navController = navController, noticiaId = noticiaId)
        }

        composable(route = AppRoutes.CREAR_USUARIO) {
            CrearUsuarioScreen(
                viewModel = usuarioViewModel,
                navController = navController
            )
        }
        composable(route = AppRoutes.CONTACTO) {
            PantallaContacto(navController = navController)
       }


        composable(route = AppRoutes.NOSOTROS) {
            PantallaNosotros(navController = navController)

        }


        // ruta q no agrega xd
        composable(route = AppRoutes.AGREGAR)
        {
            AgregarProducto()
        }

        composable(route= AppRoutes.MAPA) {
            Geolocalizacion(navController= navController)
        }


        composable(route =  AppRoutes.COMPRA_FINAL) {
            FinalizarCompraScreen(navController = navController)
        }


    }






}

