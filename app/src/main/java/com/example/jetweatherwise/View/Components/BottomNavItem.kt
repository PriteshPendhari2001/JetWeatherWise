package com.example.jetweatherwise.View.Components

import com.example.jetweatherwise.Model.NavigationRoutes.NavigationRoutes
import com.example.jetweatherwise.R


data class BottomNavItem(val route: String,val icon : Int, val label : String)

val BottomNavItems = listOf(


    BottomNavItem(
        route = NavigationRoutes.WeatherScreen.route,
        icon = R.drawable.home,
        label = "Home"
    ),

    BottomNavItem(
        route = NavigationRoutes.FavoriteScreen.route,
        icon = R.drawable.favourites,
        label = "Favourites"
    ),

    BottomNavItem(
        route = NavigationRoutes.SettingsScreen.route,
        icon = R.drawable.setting,
        label = "Settings"
    )
)
