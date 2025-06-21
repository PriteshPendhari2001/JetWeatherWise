package com.example.jetweatherwise.Model.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoriteWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteWeatherEntity)

    @Query("SELECT * FROM favorite_weather")
    fun getAllFavorites(): Flow<List<FavoriteWeatherEntity>>

    @Query("SELECT * FROM favorite_weather WHERE city = :city LIMIT 1")
    suspend fun getFavoriteByCity(city: String): FavoriteWeatherEntity?

    @Delete
    suspend fun delete(favorite: FavoriteWeatherEntity)

    @Update
    suspend fun update(favorite: FavoriteWeatherEntity)

}