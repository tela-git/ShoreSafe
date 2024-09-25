package com.example.shoresafe.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Beach(
    val id: Int,
    val city: String,
    val isFav: Boolean,
    val latitude: String,
    val longitude: String,
    val name: String,
    val uri: String?
)
