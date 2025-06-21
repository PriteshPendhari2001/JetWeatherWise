package com.example.jetweatherwise.Model.NavigationRoutes



open class NavigationRoutes(val route : String){

    object SplashScreen : NavigationRoutes("SplashScreen")
    object WeatherScreen : NavigationRoutes("WeatherScreen")

    //BottomNavigation
    object FavoriteScreen : NavigationRoutes("FavoriteScreen")
    object SettingsScreen : NavigationRoutes("SettingScreen")
}