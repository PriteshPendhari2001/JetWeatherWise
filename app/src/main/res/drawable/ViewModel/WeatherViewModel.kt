package com.example.weatherwise.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherwise.Model.WeatherApi
import com.example.weatherwise.Model.WeatherData.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class WeatherViewModel : ViewModel() {

    private val _weatherData = MutableStateFlow<WeatherResponse?>(null)
    val weatherData: StateFlow<WeatherResponse?> = _weatherData

    private val weatherApi = WeatherApi.create()

    fun fetchWeather(city: String, apikey: String) {

        viewModelScope.launch {

            try {
                val response = weatherApi.getWeather(city, apikey)
                _weatherData.value = response

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
