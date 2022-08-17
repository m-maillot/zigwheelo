package fr.racomach.api.response

import kotlinx.serialization.Serializable

@Serializable
data class FindParksResult(
    val park: List<FindParkResult>,
)

@Serializable
data class FindParkResult(
    val id: String,
    val address: String,
    val spots: Int,
    val location: PositionResult,
)