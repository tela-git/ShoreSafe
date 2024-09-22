package com.example.shoresafe.network

import com.example.weathersamachar.data.model.PlacesSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceSearchApi {
    @GET("geocode/v1/json")
    suspend fun findPlace(
        @Query("q") name: String,
        @Query("countrycode") countryCode: String = "in",
        @Query("key") apiKey: String
    ) : PlacesSearchResponse
}
