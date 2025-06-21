package com.example.weatherwise.Model.WeatherData

data class WeatherResponse(
    val name: String,
    val main: Main,
    val clouds: Clouds,
    val weather: List<Weather>,
    val wind: Wind
)