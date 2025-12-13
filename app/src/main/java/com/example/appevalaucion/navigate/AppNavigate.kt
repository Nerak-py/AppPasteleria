package com.example.appevalaucion.navigate

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.appevalaucion.ui.screen.HistorialComprasScreen
import com.example.appevalaucion.ui.screen.PantallaContacto
import com.example.appevalaucion.ui.screen.PantallaNosotros
import com.example.appevalaucion.ui.screen.PantallaNoticiaDetalle
import com.example.appevalaucion.ui.screen.PantallaNoticias
import com.example.appevalaucion.ui.screen.PastelitosScreen
import com.example.appevalaucion.ui.screen.PerfilUsuarioScreen
import com.example.appevalaucion.viewmodel.ComprasViewModel
import com.example.appevalaucion.viewmodel.LoginViewModel

object AppRoutes {
    const val LOGIN = "login"
    const val BIENVENIDA = "bienvenida" // ✅ Sin parámetro
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

    const val APITEST = "api_test"
    const val PERFIL = "perfil"
    const val HISTORIAL_COMPRAS = "historial_compras"
}

@Composable
fun AppNavigate(navController: NavHostController) {
    val usuarioViewModel: UsuarioViewModel = viewModel()
    val productosViewModel: ProductosViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()
    val comprasViewModel: ComprasViewModel = viewModel()
    val carritoViewModel: CarritoViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.LOGIN
    ) {
        composable(route = AppRoutes.LOGIN) {
            LoginScreen(usuarioViewModel, loginViewModel, navController)
        }

        composable(route = AppRoutes.BIENVENIDA) {
            BienvenidaScreen(
                viewModel = usuarioViewModel,
                navController = navController
            )
        }

        composable(route = AppRoutes.HOME) {
            Home(navController= navController,
                productosViewModel= productosViewModel)
        }

        // ✅ Ofertas obtiene el usuarioId del ViewModel
        composable(route = AppRoutes.OFERTAS) {
            val usuario by usuarioViewModel.usuario.collectAsState()
            val usuarioId = usuario?.id?.toLongOrNull() ?: 0L

            ProductosScreen(
                navController = navController,
                productosViewModel = productosViewModel,
                carritoViewModel = carritoViewModel,
                usuarioId = usuarioId, // ✅ ID real del usuario
                isOfertas = true
            )
        }

        // ✅ Productos obtiene el usuarioId del ViewModel
        composable(route = AppRoutes.PRODUCTOS) {
            val usuario by usuarioViewModel.usuario.collectAsState()
            val usuarioId = usuario?.id?.toLongOrNull() ?: 0L

            ProductosScreen(
                navController = navController,
                productosViewModel = productosViewModel,
                carritoViewModel = carritoViewModel,
                usuarioId = usuarioId, // ✅ ID real del usuario
                isOfertas = false
            )
        }

        composable(AppRoutes.CARRITO) {
            val usuario by usuarioViewModel.usuario.collectAsState()
            val usuarioId = usuario?.id?.toLongOrNull() ?: 0L
            CarritoScreen(
                navController = navController,
                carritoViewModel = carritoViewModel,
                usuarioId = usuarioId // ✅ Pasa el usuarioId
            )
        }

        composable(route = AppRoutes.COMPRA) {
            val usuario by usuarioViewModel.usuario.collectAsState()
            val usuarioId = usuario?.id?.toLongOrNull() ?: 0L

            CompraScreen(
                navController = navController,
                carritoViewModel = carritoViewModel,
                comprasViewModel = comprasViewModel
            )
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

        composable(route = AppRoutes.APITEST) {
            PastelitosScreen()
        }

        composable(route = AppRoutes.MAPA) {
            Geolocalizacion(navController = navController)
        }

        composable(route = AppRoutes.COMPRA_FINAL) {
            FinalizarCompraScreen(
                navController = navController,
                carritoViewModel = carritoViewModel
            )
        }

        composable(route = AppRoutes.PERFIL) {
            PerfilUsuarioScreen(
                viewModel = usuarioViewModel,
                navController = navController
            )
        }

        composable(route = AppRoutes.HISTORIAL_COMPRAS) {
            HistorialComprasScreen(
                navController = navController,
                viewModel = comprasViewModel
            )
        }
    }
}
