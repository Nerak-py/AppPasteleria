package com.example.appevalaucion.model


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Login
import androidx.compose.material.icons.outlined.AccessibilityNew
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.Contacts
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Login
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.RestartAlt
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.sharp.LocalOffer
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.appevalaucion.navigate.AppNavigate
import com.example.appevalaucion.navigate.AppRoutes


//  OBJETOS BARRA LATERAL DE NAVEGACION

sealed class Items_Menu(
    val icon: ImageVector,
    val title: String,
    val ruta: String
){
    object Items_Menu1 : Items_Menu(
        Icons.Outlined.Home,
        "Inicio",
        AppRoutes.HOME
    )
    object Items_Menu2 : Items_Menu(
        Icons.Outlined.AccessibilityNew,
        "Nosotros",
        AppRoutes.NOSOTROS
    )

    object Items_Menu3 : Items_Menu(
        Icons.Outlined.Book,
        "Blogs",
        AppRoutes.BLOG
    )
    object Items_Menu4 : Items_Menu(
        Icons.Sharp.LocalOffer,
        "Ofertas",
        AppRoutes.OFERTAS
    )
    object Items_Menu5 : Items_Menu(
        Icons.Outlined.Cake,
        "Productos",
        AppRoutes.PRODUCTOS
    )
    object Items_Menu6 : Items_Menu(
        Icons.Outlined.ShoppingCart,
        "Carrito de Compras",
        AppRoutes.CARRITO
    )
    object Items_Menu7 : Items_Menu(
        Icons.Outlined.ShoppingBag,
        "Compra",
        AppRoutes.COMPRA
    )
    object Items_Menu8 : Items_Menu(
        Icons.AutoMirrored.Outlined.Login,
        "Crear Usuario",
        AppRoutes.CREAR_USUARIO
    )
    object Items_Menu9 : Items_Menu(
        Icons.Outlined.Contacts,
        "Contacto",
        AppRoutes.CONTACTO
    )
    object Items_Menu10 : Items_Menu(
        Icons.AutoMirrored.Outlined.Login,
        "Menu",
        AppRoutes.LOGIN)

    object Items_Menu11 : Items_Menu(
        Icons.Outlined.RestartAlt,
        "Api Test",
        AppRoutes.APITEST)





}