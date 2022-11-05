package fr.racomach.event.sourcing.event

import fr.racomach.values.Trip
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
sealed class CyclistEvent : Event {
    @Serializable
    data class Created(val username: String) : CyclistEvent()
    @Serializable
    data class TripAdded(val trip: Trip, val roundSchedule: LocalTime?, val name: String?) : CyclistEvent()
}


