package com.example.jetweatherwise

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.example.jetweatherwise.Model.NavigationRoutes.WeatherNavigation
import com.example.jetweatherwise.ui.theme.JetWeatherWiseTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetWeatherWiseTheme {
                WeatherNavigation()
            }
        }
    }
}
