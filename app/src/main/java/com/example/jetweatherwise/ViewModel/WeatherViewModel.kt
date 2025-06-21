package com.example.jetweatherwise.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetweatherwise.Model.Room.AppDatabase
import com.example.jetweatherwise.Model.Room.FavoriteWeatherEntity
import com.example.jetweatherwise.Model.WeatherApi
import com.example.weatherwise.Model.WeatherData.WeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed class WeatherUiState {

    object Idle : WeatherUiState()
    object Loading : WeatherUiState()
    data class Success(val data: WeatherResponse) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}

class WeatherViewModel(application: Application) :  AndroidViewModel(application) {


    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
    val uiState: StateFlow<WeatherUiState> = _uiState

    private val weatherApi = WeatherApi.create()
    private val favoriteDao = AppDatabase.getDatabase(application).favoriteWeatherDao()

    private val _isSaved = MutableStateFlow(false)
    val isSaved: StateFlow<Boolean> = _isSaved

    fun fetchWeather(city: String, apikey: String) {
        if (city.isBlank()) {
            _uiState.value = WeatherUiState.Error("Please enter a city name.")
            return
        }

        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading
            try {
                val response = weatherApi.getWeather(city, apikey)
                _uiState.value = WeatherUiState.Success(response)
            } catch (e: IOException) {
                _uiState.value = WeatherUiState.Error("No internet connection. Please check your connection and try again.")
            } catch (e: HttpException) {
                _uiState.value = WeatherUiState.Error("Please Check Your State Name: ${e.code()} ${e.message()}")
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = WeatherUiState.Error("Unexpected error occurred. Please try again later.")
            }
        }
    }

    fun saveToFavorites(data: WeatherResponse) {
        viewModelScope.launch {
            val entity = FavoriteWeatherEntity(
                city = data.name,
                temperature = data.main.temp.toDouble(),
                humidity = data.main.humidity.toInt(),
                windSpeed = data.wind.speed.toDouble()
            )
            favoriteDao.insert(entity)
            _isSaved.value = true
        }
    }

    fun checkIfCityIsSaved(city: String) {
        viewModelScope.launch {
            val isAlreadySaved = favoriteDao.getFavoriteByCity(city) != null
            _isSaved.value = isAlreadySaved
        }
    }

    fun toggleFavorite(item: FavoriteWeatherEntity) {
        viewModelScope.launch {
            favoriteDao.update(item.copy(isFavorite = !item.isFavorite))
        }
    }

    fun removeFromFavorites(city: String) {
        viewModelScope.launch {
            val existing = favoriteDao.getFavoriteByCity(city)
            if (existing != null) {
                favoriteDao.delete(existing)
                _isSaved.value = false
            }
        }
    }

    fun deleteFavorite(item: FavoriteWeatherEntity) {
        viewModelScope.launch {
            favoriteDao.delete(item)
        }
    }

    fun getAllFavorites(): Flow<List<FavoriteWeatherEntity>> = favoriteDao.getAllFavorites()

}

