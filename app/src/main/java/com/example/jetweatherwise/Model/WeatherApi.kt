package com.example.jetweatherwise.Model

import com.example.weatherwise.Model.WeatherData.WeatherResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {

    @GET("weather")

    suspend fun getWeather(
        @Query("q") city : String,
        @Query("appid") apikey : String,
        @Query("units") units :String = "metric"
    ): WeatherResponse



    companion object {

        private val base_Url = "https://api.openweathermap.org/data/2.5/"

        fun create() : WeatherApi {

            val retrofit  = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(base_Url).build()

            return retrofit.create(WeatherApi::class.java)
        }
    }
}