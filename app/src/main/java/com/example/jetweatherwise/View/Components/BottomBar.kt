package com.example.jetweatherwise.View.Components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jetweatherwise.ui.theme.skyblue



@Composable
fun BottomBar(navController: NavController){

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(containerColor = Color.Transparent) {
        BottomNavItems.forEach { item ->
            NavigationBarItem(
                icon = { Icon(painter = painterResource(id = item.icon),contentDescription = {item.label}.toString()) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = skyblue.copy(alpha = 0.9f),
                    selectedTextColor = skyblue.copy(alpha = 0.9f),
                    indicatorColor = Color.Transparent
                ),
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}