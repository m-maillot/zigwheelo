package fr.racomach.zigwheelo.parks.model

data class Park(
    val id: String,
    val address: String,
    val location: Position,
)

data class Position(
    val latitude: Double,
    val longitude: Double,
)