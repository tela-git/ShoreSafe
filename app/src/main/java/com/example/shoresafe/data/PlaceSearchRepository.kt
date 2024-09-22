package com.example.shoresafe.data


import com.example.shoresafe.network.PlaceSearchApi
import com.example.weathersamachar.data.model.PlacesSearchResponse

interface PlaceSearchRepository {
    suspend fun findPlace(name: String, apiKey: String): PlacesSearchResponse
}

class PlacesSearchRepoImpl(
    private val placeSearchApi: PlaceSearchApi
): PlaceSearchRepository {
    override suspend fun findPlace(
        name: String, apiKey: String
    ): PlacesSearchResponse {
        return placeSearchApi.findPlace(
            name = name,
            apiKey = apiKey
        )
    }
}