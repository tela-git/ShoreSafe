package com.example.shoresafe.data.model.beachsearch

import kotlinx.serialization.Serializable

@Serializable
data class Beach(
    val id: Int,
    val city: String,
    val isFav: Boolean,
    val latitude: String,
    val longitude: String,
    val name: String,
    val uri: String?,
    val state: String
)
