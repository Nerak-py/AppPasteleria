package com.example.appevalaucion.model

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.appevalaucion.navigate.AppNavigate
import com.example.appevalaucion.navigate.AppRoutes


// OBJETOS BARRA NAVEGACION INFERIOR

sealed class Items_bottom_nav (
    val icon: ImageVector,
    val title: String,
    val ruta: String
){
    object Item_bottom_nav_1: Items_bottom_nav(
        Icons.Outlined.AccountBox,
        "Perfil",
        AppRoutes.PERFIL
    )
    object Item_bottom_nav_2: Items_bottom_nav(
        Icons.Outlined.ShoppingBag,
        "Carrito",
        AppRoutes.CARRITO
    )
    object Item_bottom_nav_3: Items_bottom_nav(
        Icons.Outlined.Map,
        "Mapa",
        AppRoutes.MAPA
    )

}