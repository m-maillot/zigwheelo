package fr.racomach.database.park

import java.util.*

@JvmInline
value class ParkId(val value: UUID) {
    override fun toString() = value.toString()
}

fun UUID.toParkId() = ParkId(this)
fun String.toParkId() = ParkId(UUID.fromString(this))

data class ParkOutput(
    val id: ParkId,
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