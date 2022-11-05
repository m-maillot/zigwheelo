package fr.racomach.api.generic

import kotlinx.serialization.Serializable

@Serializable
data class Position(
    val latitude: Double,
    val longitude: Double,
)