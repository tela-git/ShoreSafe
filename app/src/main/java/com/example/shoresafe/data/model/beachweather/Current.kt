package com.example.shoresafe.data.model.beachweather

data class Current(
    val interval: Int,
    val ocean_current_velocity: Double,
    val swell_wave_height: Double,
    val time: String,
    val wave_height: Double,
    val wind_wave_height: Double
)