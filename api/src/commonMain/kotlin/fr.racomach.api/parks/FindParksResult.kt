package fr.racomach.api.parks

import kotlinx.serialization.Serializable

@Serializable
data class FindParksResult(
    val park: List<Park>,
) {
    @Serializable
    data class Park(
        val id: String,
        val address: String,
        val location: Position,
    )
}