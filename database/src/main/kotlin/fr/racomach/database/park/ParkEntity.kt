package fr.racomach.database.park

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.id.toId
import org.litote.kmongo.newId

@Serializable
data class ParkEntity(
    val externalId: String,
    val address: String,
    val city: String,
    val place: String,
    val sheltered: Boolean,
    val spot: Int,
    val location: Location,
    val valid: Boolean,
    @SerialName("_id")
    @Contextual
    val id: Id<ParkEntity> = newId(),
) {
    @Serializable
    data class Location(
        val coordinates: List<Double>,
        val type: String = "Point",
    )
}