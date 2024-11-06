package com.example.shoresafe.network

import com.example.shoresafe.data.model.beachweather.MarineWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface BeachWeatherApi {
    @GET("v1/marine")
    suspend fun getBeachWeather(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("daily") dailyParameters: String = "wave_height_max,wind_wave_height_max,swell_wave_height_max",
        @Query("current") currentParameters: String = "wave_height,wind_wave_height,swell_wave_height,ocean_current_velocity",
        @Query("past_days") pastDays: Int,
        @Query("timezone_abbreviation") timeZone: String
    ): MarineWeather
}
