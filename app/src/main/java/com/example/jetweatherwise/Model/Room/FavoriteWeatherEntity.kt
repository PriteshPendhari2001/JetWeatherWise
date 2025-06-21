package com.example.jetweatherwise.Model.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_weather")
data class FavoriteWeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val city: String,
    val temperature: Double,
    val humidity: Int,
    val windSpeed: Double,
    val timestamp: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = true
)