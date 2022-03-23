package fr.racomach.api.parks

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