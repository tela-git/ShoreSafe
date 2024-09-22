package com.example.weathersamachar.data.model

data class Result(
    val annotations: com.example.weathersamachar.data.model.Annotations,
    val bounds: com.example.weathersamachar.data.model.Bounds,
    val components: Components,
    val confidence: Int,
    val formatted: String,
    val geometry: com.example.weathersamachar.data.model.Geometry
)