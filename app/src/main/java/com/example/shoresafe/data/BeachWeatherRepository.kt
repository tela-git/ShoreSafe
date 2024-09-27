package com.example.shoresafe.data

import com.example.shoresafe.data.model.beachweather.MarineWeather
import com.example.shoresafe.network.BeachWeatherApi

interface BeachWeatherRepository {
    suspend fun getBeachWeather(
        latitude: String,
        longitude: String,
        dailyParameters: String = "wave_height_max,wind_wave_height_max,swell_wave_height_max",
        currentParameters: String = "wave_height,wind_wave_height,swell_wave_height,ocean_current_velocity",
        pastDays: Int = 1
    ): MarineWeather
}

class BeachWeatherRepositoryImpl(
    private val beachWeatherApi: BeachWeatherApi
): BeachWeatherRepository {
    override suspend fun getBeachWeather(
        latitude: String,
        longitude: String,
        dailyParameters: String,
        currentParameters: String,
        pastDays: Int
    ): MarineWeather {
        return beachWeatherApi.getBeachWeather(
            latitude = latitude,
            longitude = longitude,
            dailyParameters = dailyParameters,
            currentParameters = currentParameters,
            pastDays = pastDays,
            timeZone = "IST"
        )
    }
}
