package com.example.shoresafe.data.model.beachweather

data class CurrentUnits(
    val interval: String,
    val ocean_current_velocity: String,
    val swell_wave_height: String,
    val time: String,
    val wave_height: String,
    val wind_wave_height: String
)