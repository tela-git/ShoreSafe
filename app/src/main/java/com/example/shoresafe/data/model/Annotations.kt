package com.example.weathersamachar.data.model

data class Annotations(
    val DMS: com.example.weathersamachar.data.model.DMS,
    val MGRS: String,
    val Maidenhead: String,
    val Mercator: com.example.weathersamachar.data.model.Mercator,
    val OSM: com.example.weathersamachar.data.model.OSM,
    val UN_M49: com.example.weathersamachar.data.model.UNM49,
    val callingcode: Int,
    val currency: com.example.weathersamachar.data.model.Currency,
    val flag: String,
    val geohash: String,
    val qibla: Double,
    val roadinfo: com.example.weathersamachar.data.model.Roadinfo,
    val sun: com.example.weathersamachar.data.model.Sun,
    val timezone: com.example.weathersamachar.data.model.Timezone,
    val what3words: com.example.weathersamachar.data.model.What3words
)