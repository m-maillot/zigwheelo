package fr.racomach.api.place.dto

import fr.racomach.api.common.Location
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val places: List<Place>,
) {
    @Serializable
    data class Place(
        val location: Location,
        val label: String,
        val score: Double,
    )
}