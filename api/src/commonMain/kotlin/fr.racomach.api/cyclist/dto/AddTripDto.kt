package fr.racomach.api.cyclist.dto

import com.benasher44.uuid.Uuid
import fr.racomach.api.common.Location
import fr.racomach.api.serializer.DurationSerializer
import fr.racomach.api.serializer.UuidSerializer
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
data class AddTripRequest(
    val from: Location,
    val to: Location,
    val schedule: LocalTime,
    @Serializable(with = DurationSerializer::class)
    val duration: Duration,
    val roundTripStart: LocalTime? = null,
    val name: String? = null,
)

@Serializable
data class AddTripResponse(
    @Serializable(with = UuidSerializer::class)
    val cyclistId: Uuid,
)
