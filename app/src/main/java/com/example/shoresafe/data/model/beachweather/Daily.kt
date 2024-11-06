package com.example.shoresafe.data.model.beachweather

data class Daily(
    val swell_wave_height_max: List<Double>,
    val time: List<String>,
    val wave_height_max: List<Double>,
    val wind_wave_height_max: List<Double>
)