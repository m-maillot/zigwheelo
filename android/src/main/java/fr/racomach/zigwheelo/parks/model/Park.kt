package fr.racomach.zigwheelo.parks.model

data class ParkModel(
    val id: String,
    val address: String,
    val location: PositionModel,
)

data class PositionModel(
    val latitude: Double,
    val longitude: Double,
)