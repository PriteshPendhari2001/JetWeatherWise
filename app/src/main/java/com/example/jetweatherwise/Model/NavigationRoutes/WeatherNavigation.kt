package com.example.jetweatherwise.Model.NavigationRoutes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetweatherwise.View.FavoriteScreen
import com.example.jetweatherwise.View.SplashScreen
import com.example.jetweatherwise.View.WeatherScreen
import com.example.jetweatherwise.ViewModel.SplashViewModel
import com.example.jetweatherwise.ViewModel.WeatherViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherNavigation(){

    val navcontroller = rememberNavController()

    NavHost(
        navController = navcontroller,
        startDestination = NavigationRoutes.SplashScreen.route
    ){
        composable(NavigationRoutes.SplashScreen.route){
            val splashViewModel: SplashViewModel = viewModel()
            SplashScreen(navcontroller,splashViewModel)
        }

        composable(NavigationRoutes.WeatherScreen.route){
            WeatherScreen(navcontroller)
        }

        composable(NavigationRoutes.FavoriteScreen.route){
            val weatherViewModel : WeatherViewModel = viewModel()
            FavoriteScreen(navcontroller,weatherViewModel)
        }

    }
}

