package fr.racomach.api.parks

import kotlinx.serialization.Serializable

@Serializable
data class Position(
    val latitude: Double,
    val longitude: Double,
)