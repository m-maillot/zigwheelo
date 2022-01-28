package fr.racomach.api.parks

import kotlinx.serialization.Serializable

@Serializable
data class FindParksRequest(
    val location: Position,
    val distanceInMeter: Int,
)