package fr.racomach.values

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val latitude: Double,
    val longitude: Double,
)

fun Location.toLocation() = fr.racomach.api.common.Location(latitude, longitude)
fun fr.racomach.api.common.Location.toLocation() = Location(latitude, longitude)
