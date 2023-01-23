package fr.racomach.event.sourcing.event

import fr.racomach.values.Trip
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
sealed class CyclistEvent : Event {

    @Serializable
    data class Created(val username: String) : CyclistEvent()

    @Serializable
    data class TripAdded(val trip: Trip, val roundSchedule: LocalTime?, val name: String?) :
        CyclistEvent()

    @Serializable
    data class NotificationSettingsUpdated(val token: String, val notificationAt: LocalTime) :
        CyclistEvent()

    @Serializable
    object NotificationSettingsRemoved : CyclistEvent()

    @Serializable
    data class NotificationReady(val token: String, val name: String) : CyclistEvent()
}


