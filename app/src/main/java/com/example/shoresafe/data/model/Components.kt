package com.example.weathersamachar.data.model

data class Components(
    val ISO_31661alpha2: String,
    val ISO_31661alpha3: String,
    val ISO_31662: List<String>,
    val _category: String,
    val _normalized_city: String,
    val _type: String,
    val city: String,
    val city_district: String,
    val continent: String,
    val country: String,
    val country_code: String,
    val county: String,
    val municipality: String,
    val postcode: String,
    val region: String,
    val road: String,
    val road_type: String,
    val state: String,
    val state_code: String,
    val state_district: String,
    val suburb: String
)