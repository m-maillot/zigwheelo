package fr.racomach.server.feature.parkAroundPosition

import kotlinx.serialization.Serializable

@Serializable
data class FindParksResult(
    val park: List<ParkResult>,
) {
    @Serializable
    data class ParkResult(
        val id: String,
        val address: String,
        val location: Position,
    )
}

@Serializable
data class Position(
    val latitude: Double,
    val longitude: Double,
)