package com.example.weathersamachar.data.model

data class PlacesSearchResponse(
    val documentation: String,
    val licenses: List<com.example.weathersamachar.data.model.License>,
    val rate: com.example.weathersamachar.data.model.Rate,
    val results: List<com.example.weathersamachar.data.model.Result>,
    val status: com.example.weathersamachar.data.model.Status,
    val stay_informed: com.example.weathersamachar.data.model.StayInformed,
    val thanks: String,
    val timestamp: com.example.weathersamachar.data.model.Timestamp,
    val total_results: Int
)