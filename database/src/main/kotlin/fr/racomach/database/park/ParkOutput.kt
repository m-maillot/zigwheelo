package fr.racomach.database.park

data class ParkOutput(
    val id: String,
    val externalId: String,
    val address: String,
    val city: String,
    val place: String,
    val sheltered: Boolean,
    val spot: Int,
    val location: Location,
) {
    data class Location(
        val latitude: Double,
        val longitude: Double,
    )
}