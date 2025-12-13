package com.example.appevalaucion.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appevalaucion.model.Items_Menu
import com.example.appevalaucion.navigate.currentRoute
import kotlinx.coroutines.launch
import com.example.appevalaucion.R

@Composable
fun MenuLateral(
    navController: NavController,
    drawerState: DrawerState,
    contenido: @Composable () -> Unit
) {

    val scope = rememberCoroutineScope()
   val menu_items = listOf(
       Items_Menu.Items_Menu1,
       Items_Menu.Items_Menu2,
       Items_Menu.Items_Menu3,
       Items_Menu.Items_Menu4,
       Items_Menu.Items_Menu5,
       Items_Menu.Items_Menu6,
       Items_Menu.Items_Menu7,
       Items_Menu.Items_Menu8,
       Items_Menu.Items_Menu9,
       Items_Menu.Items_Menu10,
       Items_Menu.Items_Menu11

   )
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null
                )
                menu_items.forEach { item ->
                    NavigationDrawerItem(
                        modifier = Modifier.padding(10.dp),
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(item.title) },
                        selected = currentRoute(navController) == item.ruta,
                        onClick =  {
                            scope.launch { drawerState.close() }
                            navController.navigate(item.ruta)
                        }
                    )
                }
            }
        }
    )
    {
        contenido()
    }
}
