package fr.racomach.api.response

import kotlinx.serialization.Serializable

@Serializable
data class PositionResult(
    val latitude: Double,
    val longitude: Double,
)