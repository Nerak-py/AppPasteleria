package com.example.appevalaucion.components

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.appevalaucion.model.Items_bottom_nav.*
import com.example.appevalaucion.navigate.currentRoute


@Composable
fun NavegacionInferior(
    navController: NavController
) {
    val menu_items = listOf(
        Item_bottom_nav_1,
        Item_bottom_nav_2,
        Item_bottom_nav_3
    )
    BottomAppBar {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface
        ) {
            menu_items.forEach { item ->
                val selected = currentRoute(navController) == item.ruta
                NavigationBarItem(
                    selected = selected,
                    onClick = {navController.navigate(item.ruta)},
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = {
                        Text(text = item.title)
                    }
                )
            }
        }
    }
}
