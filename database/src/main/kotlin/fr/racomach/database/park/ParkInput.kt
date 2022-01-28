package fr.racomach.database.park

data class ParkInput(
    val externalId: String,
    val address: String,
    val city: String,
    val place: String,
    val sheltered: Boolean,
    val spot: Int,
    val longitude: Double,
    val latitude: Double,
)