package fr.racomach.values

import fr.racomach.api.serializer.DurationSerializer
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
data class Trip(
    val from: Location,
    val to: Location,
    val schedule: LocalTime,
    @Serializable(with = DurationSerializer::class)
    val duration: Duration,
)